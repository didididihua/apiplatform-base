package cn.chong.apimook.client;

import cn.chong.apimook.constant.CommonConstant;
import cn.chong.apimook.entity.User;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--02 14:08
 * @description
 */
@Data
@Slf4j
@NoArgsConstructor
public class UserClient {


    private String accessKey;
    //用于加密签名，不能进行传输，让客户端根据accessKey到数据库中查询
    private String secretKey;


    public UserClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 将api签名认证需要的参数设置在请求头中
     */
    private HashMap setSginHeader(String body) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(CommonConstant.ACCESS_KEY, accessKey);
        hashMap.put(CommonConstant.NONCE, RandomUtil.randomNumbers(5));
        hashMap.put(CommonConstant.BODY, body);
        hashMap.put(CommonConstant.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        hashMap.put(CommonConstant.SIGN, getSgin(body, secretKey));
        return hashMap;
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


    /**
     * 根据传入的user，返回其user的name
     * @param user 用于封装name信息的user对象
     * @return 响应结果
     */
    public String getNameByPost(User user){
        Gson gson = new Gson();
        String json = gson.toJson(user);
        //加签
        String response = HttpRequest.post( CommonConstant.HOST_URL +"/api/name/")
                .headerMap(setSginHeader(json), false)
                .body(json)
                .execute().body();
        log.info("getNameByPost()---->" + response);
        return response;
    }

    public String getNameByGet(String name){
        String result3= HttpUtil.get( CommonConstant.HOST_URL + "/api/name/?name=" + name);
        log.info("clien name:" + name);
        return result3;
    }

}
