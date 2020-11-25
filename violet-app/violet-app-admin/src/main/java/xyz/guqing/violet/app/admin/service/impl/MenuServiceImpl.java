package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.guqing.violet.app.admin.mapper.MenuMapper;
import xyz.guqing.violet.app.admin.model.enums.MenuType;
import xyz.guqing.violet.app.admin.model.params.MenuQuery;
import xyz.guqing.violet.app.admin.service.MenuService;
import xyz.guqing.violet.common.core.model.dto.RouterMeta;
import xyz.guqing.violet.common.core.model.dto.VueRouter;
import xyz.guqing.common.support.model.dto.MenuTree;
import xyz.guqing.common.support.model.entity.system.Menu;
import xyz.guqing.violet.common.core.utils.ServiceUtils;
import xyz.guqing.violet.common.core.utils.TreeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> listUserMenus(String username) {
        return this.baseMapper.findUserMenus(username);
    }

    @Override
    public List<MenuTree> listTreeMenus(MenuQuery menuQuery) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(menuQuery.getTitle())) {
            queryWrapper.like(Menu::getTitle, menuQuery.getTitle());
        }
        queryWrapper.orderByAsc(Menu::getSortIndex);
        List<Menu> menus = list(queryWrapper);

        List<MenuTree> menuTrees = convertTo(menus);
        if (StringUtils.equals(menuQuery.getType(), MenuType.BUTTON.getValue())) {
            return menuTrees;
        } else {
            return TreeUtil.build(menuTrees);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenus(List<Long> menuIds) {
        delete(menuIds);
    }

    /**
     * 根据菜单id集合递归删除菜单
     *
     * @param menuIds 菜单id集合
     */
    private void delete(List<Long> menuIds) {
        // 根据id集合删除菜单
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = list(queryWrapper);

        if (CollectionUtils.isEmpty(menus)) {
            // 如果非空则返回终止递归
            return;
        }

        List<Long> menuIdList = menus.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        // 递归
        this.delete(menuIdList);
    }

    private List<MenuTree> convertTo(List<Menu> menus) {
        return ServiceUtils.convertToList(menus, menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getId().toString());
            tree.setValue(tree.getId());
            tree.setKey(tree.getId());
            tree.setParentId(menu.getParentId().toString());
            tree.setTitle(menu.getTitle());
            tree.setIcon(menu.getIcon());
            tree.setType(menu.getType());
            return tree;
        });
    }

    @Override
    public List<VueRouter<Menu>> listUserRouters(String username) {
        List<Menu> menus = this.listUserMenus(username);

        List<VueRouter<Menu>> routes = ServiceUtils.convertToList(menus, menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            BeanUtils.copyProperties(menu, route);

            RouterMeta routerMeta = new RouterMeta();
            BeanUtils.copyProperties(menu, routerMeta);
            route.setMeta(routerMeta);
            return route;
        });

        return TreeUtil.buildVueRouter(routes);
    }

}
