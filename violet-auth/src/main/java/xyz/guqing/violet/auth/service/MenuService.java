package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.entity.system.Menu;

import java.util.List;
import java.util.Map;

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

    /**
     * 获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<Menu>> getUserRouters(String username);

    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> findUserMenus(String username);
}
