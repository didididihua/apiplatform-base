package cn.chong.apimook.util;

import cn.chong.apimook.constant.CommonConstant;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

import java.util.HashMap;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--18 22:58
 * @description
 */
public class ClientUtil {

    /**
     * 将api签名认证需要的参数设置在请求头中
     */
    public static HashMap setSginHeader(String body, String accessKey, String secretKey) {
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
    public static String getSgin(String body, String secreKey) {

        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String digestHex = md5.digestHex(body + "." + secreKey);
        return digestHex;

    }
}
