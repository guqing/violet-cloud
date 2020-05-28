package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.mapper.UserMapper;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public CurrentUser loadUserByUsername(String username) {
        Optional<CurrentUser> userOptional = baseMapper.findByUsername(username);
        return userOptional.orElseThrow(() -> new NotFoundException("用户不存在"));
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        User user = getOne(queryWrapper);
        if(user == null) {
            throw new NotFoundException("用户不存在");
        }
        return user;
    }

}
