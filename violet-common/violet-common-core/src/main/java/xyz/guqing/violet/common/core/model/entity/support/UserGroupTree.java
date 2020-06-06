package xyz.guqing.violet.common.core.model.entity.support;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<UserGroup> {
    private Integer orderIndex;
    private String key;
}
