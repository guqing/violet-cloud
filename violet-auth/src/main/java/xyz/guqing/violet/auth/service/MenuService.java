package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.entity.system.Menu;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface MenuService extends IService<Menu> {
    /**
     * 查询用户权限集合字符串
     * @param username 用户名
     * @return 如果查询到返回权限集合字符串
     */
    String findUserPermissions(String username);
}
