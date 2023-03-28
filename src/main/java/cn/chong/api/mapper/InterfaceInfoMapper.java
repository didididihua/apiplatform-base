package cn.chong.api.mapper;

import cn.chong.common.model.entity.InterfaceInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 接口信息表
 * 
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-01 20:24:06
 */
@Mapper
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfoEntity> {

    List<InterfaceInfoEntity> getTopInterfaceInfo(@Param("topInterfaceinfoNum")Integer topInterfaceinfoNum);
}
