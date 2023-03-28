package cn.chong.api.service;

import cn.chong.api.model.vo.InterfaceInfoVO;
import cn.chong.common.model.entity.UserInterfaceInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户调用接口关系表
 *
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-04 16:30:16
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfoEntity> {

    /**
     * 对应用户的对应接口调用次数+1
     * @param interfaceInfoId 接口id
     * @param userId 用户id
     * @return >0 成功
     */
    int invokCount(Long interfaceInfoId,Long userId);

    /**
     * 获取前topInterfaceinfoNum个调用量的接口
     * @param topInterfaceinfoNum top n 的 n
     * @return 接口信息list
     */
    List<InterfaceInfoVO> analysisInterfaceInfo(Integer topInterfaceinfoNum);
}

