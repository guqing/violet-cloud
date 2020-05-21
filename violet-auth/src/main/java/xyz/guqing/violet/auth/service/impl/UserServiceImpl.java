package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.User;
import xyz.guqing.violet.auth.model.mapper.UserMapper;
import xyz.guqing.violet.auth.service.UserService;

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

}
