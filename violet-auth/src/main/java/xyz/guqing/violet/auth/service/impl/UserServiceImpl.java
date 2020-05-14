package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.mapper.UserMapper;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.entity.system.SystemUser;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SystemUser> implements UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public SystemUser loadUserByUsername(String username) {
        return userMapper.findByName(username);
    }
}
