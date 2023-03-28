package cn.chong.api.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--05 12:47
 * @description
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;

    private static final long serialVersionUID = 1L;
}
