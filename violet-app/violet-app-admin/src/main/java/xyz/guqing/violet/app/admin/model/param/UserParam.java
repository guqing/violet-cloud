package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.CreateCheck;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.common.core.model.support.UpdateCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author guqing
 * @date 2020-06-01
 */
@Data
public class UserParam implements InputConverter<User> {
    private Long id;

    @NotBlank(message = "用户名不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空", groups = {CreateCheck.class})
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    @NotEmpty(message = "用户角色不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private List<Long> roleIds;

    private Long groupId;
}
