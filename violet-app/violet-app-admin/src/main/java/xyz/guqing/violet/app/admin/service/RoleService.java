package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.model.param.RoleQuery;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.support.PageInfo;

import java.util.List;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-03
 */
public interface RoleService extends IService<Role> {
    /**
     * 保存用户和角色关系
     * @param userId 用户id
     * @param roleIds 角色id集合
     */
    void saveUserRoles(Long userId, List<Long> roleIds);

    /**
     * 根据条件查询角色信息
     * @param roleQuery 查询条件
     * @return 返回分页角色列表
     */
    PageInfo<RoleDTO> listBy(RoleQuery roleQuery);

    /**
     * 创建或更新角色和角色关联的菜单信息
     * @param role 角色信息
     * @param menuIds 角色关联的菜单集合
     */
    void createOrUpdate(Role role, Set<Long> menuIds);
}
