package cn.chong.api.service.inner;

import cn.chong.api.common.ErrorCode;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.service.InterfaceInfoService;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import cn.chong.common.service.InnerInterfaceInfoService;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.http.protocol.HTTP;

import javax.annotation.Resource;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:09
 * @description 用于给内部服务调用的接口
 */

@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public InterfaceInfoEntity getInterfaceInfoByPathAndMethod(String path, String method) {

        if(StrUtil.isAllBlank(path, method)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "path或method参数为空");
        }

        //验证请求方法是否合法
        validMethod(method);

        //构造查询条件：请求的url与请求方式method需要一致
        LambdaQueryWrapper<InterfaceInfoEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(InterfaceInfoEntity::getMethod, method)
                .eq(InterfaceInfoEntity::getUrl, path);

        InterfaceInfoEntity one = interfaceInfoService.getOne(wrapper);

        return one;
    }

    /**
     * 验证请求方式是否合法
     * @param method 网关传过来的对mook接口模块的请求方式
     */
    private void validMethod(String method) {

        if(!("GET".equals(method) || "POST".equals(method)
            || "PUT".equals(method) || "DELETE".equals(method))){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求方式：" + method + "不合法");
        }

    }

}
