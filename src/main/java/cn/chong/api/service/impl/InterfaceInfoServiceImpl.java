package cn.chong.api.service.impl;

import cn.chong.api.common.ErrorCode;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.mapper.InterfaceInfoMapper;
import cn.chong.api.model.dto.interfaceInfo.IdRequest;
import cn.chong.api.model.vo.InterfaceInfoVO;
import cn.chong.apimook.client.DidididiClient;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import cn.chong.common.model.enums.InterfaceStatusEnum;
import cn.chong.api.service.InterfaceInfoService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("interfaceInfoService")
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfoEntity> implements InterfaceInfoService {


    @Override
    public void validInterfaceInfo(InterfaceInfoEntity interfaceInfo, boolean b) {

        if(interfaceInfo == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String method = interfaceInfo.getMethod();
        String requestheader = interfaceInfo.getRequestHeader();
        String responseheader = interfaceInfo.getResponseHeader();
        String url = interfaceInfo.getUrl();
        String userid = interfaceInfo.getUserid();
        Integer status = interfaceInfo.getStatus();
        String requestParam = interfaceInfo.getRequestParam();

        if(b){
            if(StrUtil.isAllBlank(name, description, method, requestheader, responseheader, url, userid) || status == null){
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }

        //todo 对url进行正则匹配

    }

    @Override
    public void hasInterfaceInfo(Long id) {

        InterfaceInfoEntity interfaceInfoEntity = baseMapper.selectById(id);

        if(interfaceInfoEntity == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该接口不存在");
        }

        if(interfaceInfoEntity.getStatus() == InterfaceStatusEnum.OFFLINE.getValue()){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "该接口已关闭");
        }

    }



}