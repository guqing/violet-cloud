package xyz.guqing.violet.auth.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-29
 */
@Data
public class UserInfoDTO {
    private Long id;

    /**
     * 用户名
     */
    private String username;

    private String nickname;

    /**
     * 用户组
     */
    private Long groupId;

    private String groupName;

    private List<String> roleIds;

    private List<String> roleNames;

    private List<String> permissions;
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

    private LocalDateTime createTime;
}
