package xyz.guqing.violet.app.admin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.common.support.model.entity.system.Role;

import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends Role {
    private Set<Long> menuIds;
}
