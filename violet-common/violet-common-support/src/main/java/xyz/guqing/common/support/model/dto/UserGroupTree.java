package xyz.guqing.common.support.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.common.support.model.entity.system.UserGroup;
import xyz.guqing.violet.common.core.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<UserGroup> {
    private Integer orderIndex;
}
