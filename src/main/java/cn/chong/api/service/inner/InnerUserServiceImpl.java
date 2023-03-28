package cn.chong.api.service.inner;

import cn.chong.api.service.UserService;
import cn.chong.common.model.entity.User;
import cn.chong.common.service.InnerUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author: tangchongjie
 * @creattime: 2023--03--07 18:10
 * @description 用于给内部服务调用的接口
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserService userService;

    @Override
    public User getUserByAccessKey(String accessKey) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccessKey, accessKey);
        User one = userService.getOne(wrapper);

        return one;
    }
}
