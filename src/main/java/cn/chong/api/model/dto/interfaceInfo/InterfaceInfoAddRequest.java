package cn.chong.api.model.dto.interfaceInfo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class InterfaceInfoAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 接口地址
     */
    private String url;
    /**
     * 请求头
     */
    private String requestHeader;
    /**
     * 响应头
     */
    private String responseHeader;
    /**
     * 创建人
     */
    private String userId;

    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 接口状态（0 - 关闭， 1 - 开启））
     */
    private Integer status;
    /**
     * 请求类型
     */
    private String method;

}