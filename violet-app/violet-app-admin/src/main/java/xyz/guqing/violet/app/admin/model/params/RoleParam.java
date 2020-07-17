package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.support.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author guqing
 * @date 2020-06-09
 */
@Data
public class RoleParam implements InputConverter<Role> {
    private Long id;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 140, message = "角色名称字符长度不能超过 {max}")
    private String roleName;

    private String remark;

    private Set<Long> menuIds;
}
