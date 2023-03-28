package cn.chong.common.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 21:56
 * @description
 */
public class CommonUtil {
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
