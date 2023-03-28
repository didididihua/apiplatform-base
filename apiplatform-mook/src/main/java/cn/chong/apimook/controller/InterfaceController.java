package cn.chong.apimook.controller;

import cn.chong.apimook.service.InterfaceService;
import cn.chong.common.model.dto.SearchRequest;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--18 23:23
 * @description
 */
@RestController
@RequestMapping("/api")
public class InterfaceController {

    @Resource
    private InterfaceService interfaceService;

    /**
     * 程序员的开始，返回一个 Hello World 字符串
     * @return
     */
    @PostMapping("/getHelloWorld")
    public String getHelloWorld(){
        return "Hello World";
    }

    /**
     * 获取一个随机的Int数
     */
    @PostMapping("/getRandomInt")
    public String getRandomInt(){
        int randomInt = RandomUtil.randomInt();
        return String.valueOf(randomInt);
    }

    /**
     * 获取当前时间
     * @return
     */
    @PostMapping("/getNowTime")
    public String getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00").format(now);
        return format.toString();
    }

    /**
     * 返回一个随机的Double数字
     * @return
     */
    @PostMapping("/getRandomDouble")
    public String getRandomDouble(){
        double v = RandomUtil.randomDouble();
        return String.valueOf(v);
    }

    @PostMapping("/getPictureUrl")
    public String getPictureUrl(@RequestBody SearchRequest searchRequest){
        if(StrUtil.isBlank(searchRequest.getSearchText())){
            return "No search terms";
        }
        return interfaceService.getPictureUrl(searchRequest);
    }

}
