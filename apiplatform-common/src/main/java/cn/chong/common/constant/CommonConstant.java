package cn.chong.common.constant;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--08 10:24
 * @description
 */
public interface CommonConstant {
    /**
     * 请求头中用户的accessKey(用户标识码)的标识
     */
    public static final String ACCESS_KEY = "accessKey";
    /**
     * 请求头中用户的body(请求体数据)的标识
     */
    public static final String BODY = "body";
    /**
     * 请求头中用户的nonce(随机数)的标识
     */
    public static final String NONCE = "nonce";
    /**
     * 请求头中用户的timestamp(时间戳)的标识
     */
    public static final String TIMESTAMP = "timestamp";
    /**
     * 请求头中用户的sign(签名)的标识
     */
    public static final String SIGN = "sign";
}
