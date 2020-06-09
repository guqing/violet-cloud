package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.entity.system.RoleMenu;

import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-09
 */
public interface RoleMenuService extends IService<RoleMenu> {
    /**
     * 根据角色id查询角色和菜单的关联关系
     * @param roleId 角色id
     * @return 查询到返回集合信息否则返回空集合
     */
    List<RoleMenu> listByRoleId(Long roleId);

    /**
     * 创建或跟新角色和菜单关联关系
     * @param roleId 角色id
     * @param menuIds 角色对应的菜单id集合
     */
    void createOrUpdate(Long roleId, Set<Long> menuIds);
}
