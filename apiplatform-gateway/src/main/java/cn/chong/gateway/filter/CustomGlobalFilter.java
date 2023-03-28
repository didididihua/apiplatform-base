package cn.chong.gateway.filter;

import cn.chong.common.constant.CommonConstant;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import cn.chong.common.model.entity.User;
import cn.chong.common.model.enums.InterfaceStatusEnum;
import cn.chong.common.service.InnerInterfaceInfoService;
import cn.chong.common.service.InnerUserInterfaceInfoService;
import cn.chong.common.service.InnerUserService;
import cn.chong.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--05 15:12
 * @description
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {


    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");

    private static final String INTERFACE_HOST = "http://www.didididih.club:8090";

    public static final Long ALLOW_REQUEST_TIME = 1000l * 60 * 5;

    public static HashSet<Integer> hashSet = new HashSet();

    @DubboReference
    public InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    public InnerUserService userService;

    @DubboReference
    public InnerUserInterfaceInfoService userInterfaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("custom global filter");
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();

        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        ServerHttpResponse response = exchange.getResponse();

        // 2.黑白名单

        // 3.用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst(CommonConstant.ACCESS_KEY);
        String nonce = headers.getFirst(CommonConstant.NONCE);
        String timestamp = headers.getFirst(CommonConstant.TIMESTAMP);
        String sign = headers.getFirst(CommonConstant.SIGN);
        String body = headers.getFirst(CommonConstant.BODY);
        String secretKey;

        // 3.1判断用户的ak,sk是否正确

        // 3.2 判断传入时间戳 是否小于 5min
        if(timestamp != null && System.currentTimeMillis() - Long.parseLong(timestamp) > ALLOW_REQUEST_TIME){
            log.info("请求：{} 的时间戳距离当时时间超过5分钟", request.getId());
            return handleNoAuth(response);
        }

        // todo: 3.3 判断随机数是否在缓存中
        if(hashSet.contains(nonce)){
            log.info("请求：{}, 携带的随机码重复，是重复接口", request.getId());
            return handleNoAuth(response);
        }

        User user = null;
        try{
            user = userService.getUserByAccessKey(accessKey);
        }catch (Exception e){
            log.info("Dubbo请求获取用户信息失败");
            return handleNoAuth(response);
        }
        if(user == null){
            log.info("Dubbo获取的请求用户不存在");
            return handleNoAuth(response);
        }

        secretKey = user.getSecretKey();
        String rSgin = CommonUtil.getSgin(body, secretKey);

        if(!rSgin.equals(sign)){
            log.info("用户非法");
            return handleNoAuth(response);
        }

        // 4.判断接口是否存在
        InterfaceInfoEntity interfaceInfo = null;
        try{
            interfaceInfo = interfaceInfoService.getInterfaceInfoByPathAndMethod(path, method);
        }catch (Exception e){
            log.info("Dubbo请求获取接口信息失败");
        }

        // 4.1 判断接口是否关闭
        if(interfaceInfo == null || InterfaceStatusEnum.OFFLINE.getValue() == interfaceInfo.getStatus()){
            log.info("当前接口不存在或者已关闭");
            return handleNoAuth(response);
        }

        // 5.请求转发
        // 6.响应日志
        // 7.成功，次数+1
        return doResponseLog(exchange, chain, interfaceInfo.getId(), user.getId());
    }

    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();

    }

    private Mono<Void> doResponseLog(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceInfoId, Long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();

            HttpStatus statusCode = originalResponse.getStatusCode();

            if(statusCode == HttpStatus.OK){
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            //
                            return super.writeWith(fluxBody.map(dataBuffer -> {

                                // 调用成功，次数+1
                                try{
                                    userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                }catch (Exception e){
                                    log.info("Dubbo调用接口次数+1失败");
                                }

                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果：" + data);

                                //log.info(sb2.toString(), rspArgs.toArray());//log.info("<-- {} {}\n", originalResponse.getStatusCode(), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        }catch (Exception e){
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
