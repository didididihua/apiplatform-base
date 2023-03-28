package cn.chong.api.service.inner;

import cn.chong.api.common.ErrorCode;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.service.UserInterfaceInfoService;
import cn.chong.api.service.UserService;
import cn.chong.common.service.InnerUserInterfaceInfoService;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:10
 * @description 用于给内部服务调用的接口
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;


    @Override
    public void invokeCount(Long interfaceInfoId, Long userId) {

        if(ObjectUtil.isAllEmpty(interfaceInfoId, userId)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        //将接口的调用次数加1
        int count = userInterfaceInfoService.invokCount(interfaceInfoId, userId);

    }
}
