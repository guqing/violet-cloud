package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import xyz.guqing.violet.app.admin.mapper.RoleMenuMapper;
import xyz.guqing.violet.app.admin.service.RoleMenuService;
import xyz.guqing.common.support.model.entity.system.RoleMenu;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-09
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    public List<RoleMenu> listByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        return list(queryWrapper);
    }

    @Override
    public void createOrUpdate(Long roleId, Set<Long> menuIds) {
        removeByRoleId(roleId);

        for(Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            // 保存
            save(roleMenu);
        }
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RoleMenu::getRoleId, roleIds);
        remove(queryWrapper);
    }

    private void removeByRoleId(Long roleId) {
        // 根据角色id删除
        LambdaQueryWrapper<RoleMenu> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoleMenu::getRoleId, roleId);
        remove(queryWrapper);
    }
}
