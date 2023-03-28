package cn.chong.api.service;

import cn.chong.api.model.dto.interfaceInfo.IdRequest;
import cn.chong.api.model.vo.InterfaceInfoVO;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 接口信息表
 *
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-01 20:24:06
 */
public interface InterfaceInfoService extends IService<InterfaceInfoEntity> {

    /**
     * 校验创建时的InterfaceInfo内容
     * @param interfaceInfo
     * @param b
     */
    void validInterfaceInfo(InterfaceInfoEntity interfaceInfo, boolean b);

    /**
     * 根据接口id判断该接口是否存在
     * @param id
     */
    void hasInterfaceInfo(Long id);

}

