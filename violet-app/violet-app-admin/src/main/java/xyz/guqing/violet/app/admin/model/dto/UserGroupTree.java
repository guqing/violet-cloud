package xyz.guqing.violet.app.admin.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.model.entity.support.Tree;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupTree extends Tree<UserGroup> {
    private Long sortIndex;
}
