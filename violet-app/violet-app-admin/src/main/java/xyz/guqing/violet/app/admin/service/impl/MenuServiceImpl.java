package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.guqing.violet.app.admin.mapper.MenuMapper;
import xyz.guqing.violet.app.admin.model.enums.MenuType;
import xyz.guqing.violet.app.admin.service.MenuService;
import xyz.guqing.violet.common.core.model.entity.constant.PageConstant;
import xyz.guqing.violet.common.core.model.entity.router.RouterMeta;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.entity.support.MenuTree;
import xyz.guqing.violet.common.core.model.entity.support.Tree;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.utils.TreeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        List<Menu> userMenus = this.baseMapper.findUserMenus(username);
        if(CollectionUtils.isEmpty(userMenus)) {
            return Collections.emptyList();
        }
        return userMenus;
    }

    @Override
    public List<MenuTree> listTreeMenus(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Menu::getOrderIndex);
        List<Menu> menus = baseMapper.selectList(queryWrapper);

        List<MenuTree> trees = new ArrayList<>();
        buildTrees(trees, menus);

        if (StringUtils.equals(menu.getType(), MenuType.BUTTON.getValue())) {
           return trees;
        } else {
            return TreeUtil.build(trees);
        }
    }

    private void buildTrees(List<MenuTree> trees, List<Menu> menus) {
        menus.forEach(menu -> {
            MenuTree tree = new MenuTree();
            tree.setId(menu.getId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getName());
            tree.setComponent(menu.getComponent());
            tree.setIcon(menu.getIcon());
            if(menu.getOrderIndex() != null) {
                tree.setOrderIndex(menu.getOrderIndex().intValue());
            }
            tree.setPath(menu.getPath());
            tree.setType(menu.getType());
            tree.setPerms(menu.getPerms());
            trees.add(tree);
        });
    }

    @Override
    public List<VueRouter<Menu>> listUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.listUserMenus(username);
        menus.forEach(menu -> {
            VueRouter<Menu> route = new VueRouter<>();
            BeanUtils.copyProperties(menu, route);

            RouterMeta routerMeta = new RouterMeta();
            BeanUtils.copyProperties(menu, routerMeta);
            route.setMeta(routerMeta);

            routes.add(route);
        });
        return TreeUtil.buildVueRouter(routes);
    }

}
