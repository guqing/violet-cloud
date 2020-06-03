package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.InputConverter;

/**
 * @author guqing
 * @date 2020-06-01
 */
@Data
public class UserParam implements InputConverter<User> {
    private String username;

    /**
     * 密码
     */
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
}
