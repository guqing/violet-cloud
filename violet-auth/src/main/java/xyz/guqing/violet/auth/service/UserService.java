package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
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
    CurrentUser loadUserByUsername(String username);

    /**
     * 根据用户名查询用户基本信息
     * @param username 用户名
     * @return 查询到返回用户基本信息，否则抛出NotFound异常
     */
    User getByUsername(String username);
}
