package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.guqing.violet.common.core.model.entity.router.RouterMeta;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.auth.mapper.MenuMapper;
import xyz.guqing.violet.auth.service.MenuService;
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
    public String findUserPermissions(String username) {
        List<Menu> userPermissions = baseMapper.findUserPermissions(username);
        return userPermissions.stream().map(Menu::getPerms).collect(Collectors.joining(","));
    }

    @Override
    public List<Menu> findUserMenus(String username) {
        List<Menu> userMenus = this.baseMapper.findUserMenus(username);
        if(CollectionUtils.isEmpty(userMenus)) {
            return Collections.emptyList();
        }
        return userMenus;
    }

    @Override
    public List<VueRouter<Menu>> getUserRouters(String username) {
        List<VueRouter<Menu>> routes = new ArrayList<>();
        List<Menu> menus = this.findUserMenus(username);
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
