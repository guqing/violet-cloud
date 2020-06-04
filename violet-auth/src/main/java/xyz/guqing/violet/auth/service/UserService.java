package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.entity.system.User;

import java.util.List;

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
}
