package cn.chong.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.chong.common.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity cn.chong.api.model.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




