package xyz.guqing.violet.app.admin.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.model.entity.system.User;

/**
 * user 数据实体
 * @author guqing
 * @date 2020-05-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDO extends User {
    private String groupName;
    private String roleId;
    private String roleName;
}
