package cn.chong.common.service;

import cn.chong.common.model.entity.InterfaceInfoEntity;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:14
 * @description 
 */
public interface InnerInterfaceInfoService {

    /**
     * 根据请求路径和请求方法来获得对应接口信息
     * @param path 请求路径
     * @param method 请求方法
     * @return 接口信息
     */
    public InterfaceInfoEntity getInterfaceInfoByPathAndMethod(String path, String method);
}
