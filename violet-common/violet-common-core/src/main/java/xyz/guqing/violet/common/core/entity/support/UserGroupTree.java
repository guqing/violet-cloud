package xyz.guqing.violet.common.core.entity.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.entity.system.Dept;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<Dept> {
    private Integer orderIndex;
}
