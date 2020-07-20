package xyz.guqing.violet.gateway.enhance.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteUser;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author guqing
 * @date 2020-07-20
 */
@Data
public class UserParam implements InputConverter<RouteUser> {
    private String id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名字符长度必须在 {min}-{max} 之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 3, max = 16, message = "密码字符长度必须在 {min}-{max} 之间")
    private String password;

    @NotBlank(message = "角色不能为空")
    private String roles;
}
