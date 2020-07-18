package xyz.guqing.common.support.model.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@Accessors(chain = true)
public class RoleMenu {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


}
