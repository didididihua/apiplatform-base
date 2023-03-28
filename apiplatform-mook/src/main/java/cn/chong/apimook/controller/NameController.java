package cn.chong.apimook.controller;

import cn.chong.apimook.entity.User;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--02 14:09
 * @description 被调用的接口，身份校验、接口校验、调用次数增加都统一在网关层面做了，这里只需要进行对请求进行处理响应
 */
@RestController
@RequestMapping("/api/name")
public class NameController {

    @GetMapping("/")
    public String getNameByGet(String name, HttpServletRequest request){
        System.out.println("GET : " + name);
        return "name : " + name;
    }

    @PostMapping("/")
    public User getNameByPost(@RequestBody User user, HttpServletRequest request){

        User name = new User();
        name.setName("我POST请求被找到了---》 " + user.name);

        return name;
    }

    /**
     * 获取签名
     * @param body
     * @param secreKey
     * @return
     */
    private String getSgin(String body, String secreKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String digestHex = md5.digestHex(body + "." + secreKey);
        return digestHex;
    }

}
