package cn.chong.api.service.impl;

import cn.chong.api.common.ErrorCode;
import cn.chong.api.exception.BusinessException;
import cn.chong.api.mapper.UserInterfaceInfoMapepr;
import cn.chong.api.model.vo.InterfaceInfoVO;
import cn.chong.api.service.InterfaceInfoService;
import cn.chong.common.model.entity.InterfaceInfoEntity;
import cn.chong.common.model.entity.UserInterfaceInfoEntity;
import cn.chong.api.service.UserInterfaceInfoService;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("userInterfaceInfoService")
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapepr, UserInterfaceInfoEntity> implements UserInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Override
    public int invokCount(Long interfaceInfoId, Long userId) {
        return baseMapper.invokCount(interfaceInfoId, userId);
    }

    @Override
    public List<InterfaceInfoVO> analysisInterfaceInfo(Integer topInterfaceinfoNum) {

        List<UserInterfaceInfoEntity> list = baseMapper.analysisInterfaceInfo(topInterfaceinfoNum);


        Map<Long, List<UserInterfaceInfoEntity>> map = list.stream()
                .collect(Collectors.groupingBy(UserInterfaceInfoEntity::getInterfaceinfoId));

        LambdaQueryWrapper<InterfaceInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(InterfaceInfoEntity::getId, map.keySet());

        //根据接口id获取到接口的完整数据
        List<InterfaceInfoEntity> interfaceinfos = interfaceInfoService.list(wrapper);

        if(ObjectUtil.isEmpty(interfaceinfos)){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        //组装数据：接口完整信息 + 被调用次数
        List<InterfaceInfoVO> collect = interfaceinfos.stream().map(item -> {

            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtil.copyProperties(item, interfaceInfoVO);
            Integer totalNum = map.get(item.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());

        return collect;
    }
}