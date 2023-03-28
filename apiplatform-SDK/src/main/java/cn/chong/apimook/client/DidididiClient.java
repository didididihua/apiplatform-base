package cn.chong.apimook.client;

import cn.chong.apimook.constant.CommonConstant;
import cn.chong.apimook.util.ClientUtil;
import cn.chong.common.model.dto.SearchRequest;
import cn.hutool.http.HttpRequest;
import com.google.gson.Gson;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--18 22:58
 * @description
 */
@Data
@Slf4j
@NoArgsConstructor
public class DidididiClient {

    private String accessKey;
    //用于加密签名，不能进行传输，让客户端根据accessKey到数据库中查询
    private String secretKey;

    public DidididiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 程序员的开始，返回一个 Hello World 字符串
     * @return
     */
    public String getHelloWorld(){
        log.info("getHelloWorld()被调用了！！！");
        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/getHelloWorld")
                .headerMap(ClientUtil.setSginHeader("null", accessKey, secretKey), false)
                .body("null")
                .execute().body();
        log.info("getHelloWorld()---->" + response);
        return response;
    }

    /**
     * 获取一个随机的Int数
     */
    public String getRandomInt(){
        log.info("getRandomInt()被调用了！！！");

        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/getRandomInt")
                .headerMap(ClientUtil.setSginHeader("null", accessKey, secretKey), false)
                .body("null")
                .execute().body();
        log.info("getRandomInt()---->" + response);
        return response;
    }

    /**
     * 获取当前时间
     * @return
     */
    public String getNowTime(){
        log.info("getNowTime()被调用了！！！");
        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/getNowTime")
                .headerMap(ClientUtil.setSginHeader("null", accessKey, secretKey), false)
                .body("null")
                .execute().body();
        log.info("getNowTime()---->" + response);
        return response;
    }

    /**
     * 获取一个随机的Double数字
     * @return
     */
    public String getRandomDouble(){
        log.info("getRandomDouble()被调用了！！！");
        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/getRandomDouble")
                .headerMap(ClientUtil.setSginHeader("null", accessKey, secretKey), false)
                .body("null")
                .execute().body();
        log.info("getRandomDouble()---->" + response);
        return response;
    }

    /**
     * 获取一个与搜索关键词相关的图片
     * @return
     */
    public String getPictureUrl(SearchRequest request){
        log.info("getRandomDouble()被调用了！！！");
        Gson gson = new Gson();
        String json = gson.toJson(request);
        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/getPictureUrl")
                .headerMap(ClientUtil.setSginHeader(json, accessKey, secretKey), false)
                .body(json)
                .execute().body();
        log.info("getRandomDouble()---->" + response);
        return response;
    }


}
