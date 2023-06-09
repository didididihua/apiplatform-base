package cn.chong.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.chong.api.model.entity.Post;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yupili
* @description 针对表【post(帖子)】的数据库操作Mapper
* @createDate 2022-09-13 16:03:41
* @Entity cn.chong.api.model.entity.Post
*/
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}




