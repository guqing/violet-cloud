package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.CreateCheck;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.common.core.model.support.UpdateCheck;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @author guqing
 * @date 2020-06-01
 */
@Data
public class UserParam implements InputConverter<User> {
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    @Size(max = 100, message = "用户名字符长度不能超过 {max}")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空", groups = {CreateCheck.class})
    @Size(min = 3, max = 16, message = "密码长度必须在 {min}-{max} 字符之间")
    private String password;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$", message = "邮箱地址格式不正确")
    private String email;

    /**
     * 联系电话
     */
    @Pattern(regexp = "^(?:(?:\\+|00)86)?1[3-9]\\d{9}$", message = "手机号码格式不正确")
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    @Size(max = 150, message = "个性签名字符长度不能大于 {max}")
    private String description;

    @NotEmpty(message = "用户角色不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private List<Long> roleIds;

    private Long groupId;
}
