package cn.chong.common.service;

import cn.chong.common.model.entity.User;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:10
 * @description
 */
public interface InnerUserService {
    /**
     * 根据accessKey来获取与之对应的用户信息
     * @param AccessKey
     * @return
     */
    public User getUserByAccessKey(String AccessKey);
}
