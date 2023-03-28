package cn.chong.api.controller;

import cn.chong.api.common.BaseResponse;
import cn.chong.api.common.ResultUtils;
import cn.chong.apimook.client.DidididiClient;
import cn.chong.common.model.dto.SearchRequest;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--18 23:36
 * @description
 */
@RestController
@RequestMapping("/invoke/interface")
public class InterfaceInvokeController {

    @Resource
    public DidididiClient client;

    /**
     * 获取HelloWorld字符串
     * @return
     */
    @PostMapping("/getHelloWorld")
    public BaseResponse<Object> getHelloWorld(){
        String helloWorld = client.getHelloWorld();
        if(StrUtil.isBlank(helloWorld)){
            return ResultUtils.success("请求失败请重试");
        }
        return ResultUtils.success(helloWorld);
    }

    /**
     * 获取一个随机的int数字
     * @return
     */
    @PostMapping("/getRandomInt")
    public BaseResponse<Object> getRandomInt(){
        String randomInt = client.getRandomInt();

        return ResultUtils.success(randomInt);
    }

    /**
     * 获取当前时间
     * @return
     */
    @PostMapping("/getNowTime")
    public BaseResponse<Object>  getNowTime(){
        String nowTime = client.getNowTime();
        return ResultUtils.success(nowTime);
    }

    @PostMapping("/getPictureUrl")
    public BaseResponse<Object> getPictureUrl(@RequestBody SearchRequest request){
        String pictureUrl = client.getPictureUrl(request);
        return ResultUtils.success(pictureUrl);
    }


}
