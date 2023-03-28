package cn.chong.api.mapper;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--04 16:41
 * @description
 */

import cn.chong.common.model.entity.UserInterfaceInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户调用接口关系表
 *
 * @author tangchongjie
 * @email 1514176166@qq.com
 * @date 2023-03-04 16:30:16
 */
@Mapper
public interface UserInterfaceInfoMapepr extends BaseMapper<UserInterfaceInfoEntity> {

    int invokCount(@Param("interfaceInfoId") Long interfaceInfoId, @Param("userId") Long userId);

    List<UserInterfaceInfoEntity> analysisInterfaceInfo(@Param("topInterfaceinfoNum") Integer topInterfaceinfoNum);
}
