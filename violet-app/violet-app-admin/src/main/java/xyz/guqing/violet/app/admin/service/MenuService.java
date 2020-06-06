package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.dto.MenuTree;
import xyz.guqing.violet.common.core.model.entity.system.Menu;

import java.util.List;

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
     * 获取用户路由
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<Menu>> listUserRouters(String username);
    /**
     * 获取用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> listUserMenus(String username);

    /**
     * 根据条件查询菜单树
     * @param menu 查询条件
     * @return 返回查询到的菜单树
     */
    List<MenuTree> listTreeMenus(Menu menu);
}
