package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.app.admin.model.params.MenuQuery;
import xyz.guqing.violet.common.core.model.dto.VueRouter;
import xyz.guqing.common.support.model.dto.MenuTree;
import xyz.guqing.common.support.model.entity.system.Menu;

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
     * 获取用户路由树
     *
     * @param username 用户名
     * @return 用户路由
     */
    List<VueRouter<Menu>> listUserRouters(String username);
    /**
     * 获取扁平化用户菜单
     *
     * @param username 用户名
     * @return 用户菜单
     */
    List<Menu> listUserMenus(String username);

    /**
     * 根据条件查询菜单树
     * @param menuQuery 查询条件
     * @return 返回查询到的菜单树
     */
    List<MenuTree> listTreeMenus(MenuQuery menuQuery);

    /**
     * 根据id集合批量递归删除菜单
     * @param menuIds 菜单id集合
     */
    void deleteMenus(List<Long> menuIds);
}
