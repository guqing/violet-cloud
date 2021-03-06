package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.common.support.utils.PageUtils;
import xyz.guqing.violet.app.admin.mapper.RoleMapper;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.model.entity.RoleDO;
import xyz.guqing.violet.app.admin.model.params.RoleQuery;
import xyz.guqing.violet.app.admin.service.RoleMenuService;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.app.admin.service.UserRoleService;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.common.support.model.entity.system.Role;
import xyz.guqing.common.support.model.entity.system.UserRole;
import xyz.guqing.violet.common.core.utils.ServiceUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-03
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final UserRoleService userRoleService;
    private final RoleMenuService roleMenuService;

    @Override
    public void saveUserRoles(Long userId, List<Long> roleIds) {
        List<UserRole> userRoles = ServiceUtils.convertToList(roleIds, roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        });

        userRoleService.saveBatch(userRoles);
    }

    @Override
    public Page<Role> listBy(RoleQuery roleQuery) {
        LambdaQueryWrapper<Role> queryWrapper = Wrappers.lambdaQuery();
        if(roleQuery.getId() != null) {
            queryWrapper.like(Role::getId, roleQuery.getId());
        }

        String roleName = roleQuery.getRoleName();
        if(StringUtils.isNotBlank(roleName)) {
            queryWrapper.like(Role::getRoleName, roleName);
        }

        String remark = roleQuery.getRemark();
        if(StringUtils.isNotBlank(remark)) {
            queryWrapper.like(Role::getRemark, remark);
        }
        queryWrapper.orderByAsc(Role::getCreateTime);
        PageQuery pageQuery = roleQuery.getPageQuery();
        return page(PageUtils.convertFrom(pageQuery), queryWrapper);
    }

    @Override
    public void createOrUpdate(Role role, Set<Long> menuIds) {
        saveOrUpdate(role);
        // 创建角色和菜单关联关系
        roleMenuService.createOrUpdate(role.getId(), menuIds);
    }

    @Override
    public RoleDTO getRoleById(Long roleId) {
        Optional<RoleDO> optionalRoleDO = this.baseMapper.findById(roleId);
        if(optionalRoleDO.isPresent()) {
            RoleDO roleDO = optionalRoleDO.get();
            return new RoleDTO().convertFrom(roleDO);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoles(List<Long> roleIds) {
        removeByIds(roleIds);

        roleMenuService.deleteByRoleIds(roleIds);
        userRoleService.deleteByRoleIds(roleIds);
    }
}
