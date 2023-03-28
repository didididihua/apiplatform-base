package cn.chong.common.service;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:10
 * @description
 */
public interface InnerUserInterfaceInfoService {

    /**
     * 传入接口id，将对应接口的调用次数+1，调用用户的可调用次数 - 1
     * @param interfaceInfoId
     * @param userId
     */
    public void invokeCount(Long interfaceInfoId, Long userId);
}
