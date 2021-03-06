package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.entity.system.UserRole;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-16
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据角色id集合批量删除关系
     * @param roleIds 角色id集合
     */
    void deleteByRoleIds(List<Long> roleIds);
}
