package xyz.guqing.violet.app.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.violet.app.admin.model.entity.RoleDO;
import xyz.guqing.violet.app.admin.model.param.RoleQuery;
import xyz.guqing.violet.common.core.model.entity.system.Role;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-03
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据条件查询角色列表
     * @param roleQuery 查询条件
     * @return 返回查询结果，查询不到返回空
     */
    List<RoleDO> findUserRoleMenu(RoleQuery roleQuery);

    /**
     * 根据查询条件统计记录数量
     * @param roleQuery 查询条件
     * @return 返回记录数
     */
    Long countRoleBy(RoleQuery roleQuery);
}
