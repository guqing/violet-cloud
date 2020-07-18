package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.common.core.model.dto.CurrentUser;
import xyz.guqing.common.support.model.entity.system.User;

import java.time.LocalDateTime;
import java.util.Optional;

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

    /**
     * 查询用户信息
     * @param username 用户名
     * @return 返回查询到的用户信息dto，用户不存在抛出异常
     */
    UserInfoDTO getUserInfo(String username);

    /**
     * 更新最后登录时间
     * @param username 用户名
     * @param loginTime 最后登录时间
     */
    void updateLastLoginTime(String username, LocalDateTime loginTime);

    /**
     * 根据邮箱地址查询用户
     * @param email 邮箱
     * @return 返回查询到的用户信息Optional,可为空
     */
    Optional<User> getByEmail(String email);
}
