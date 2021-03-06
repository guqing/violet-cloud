package xyz.guqing.common.support.model.entity.system;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.common.support.model.entity.BaseEntity;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 用户组
     */
    private Long groupId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 性别 0男 1女 2保密
     */
    private Integer gender;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 描述
     */
    private String description;

    /**
     * 最近访问时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 状态 0锁定 1有效
     */
    private Integer status;

    /**
     * 删除状态：0未删除，1已删除
     */
    @TableLogic
    private Integer deleted;
}
