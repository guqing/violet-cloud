package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.entity.system.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 如果查询到返回用户信息，否则抛出NotFoundException
     */
    User loadUserByUsername(String username);
}
