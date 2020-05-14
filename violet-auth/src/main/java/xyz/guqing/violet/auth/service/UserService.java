package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.entity.system.SystemUser;

/**
 * @author guqing
 * @date 2020-05-14
 */
public interface UserService extends IService<SystemUser> {
    /**
     * 根据用户名查询用户信息关联查询用户角色和权限信息
     * @param username 用户名
     * @return 返回用户信息角色信息和权限信息
     */
    SystemUser loadUserByUsername(String username);
}
