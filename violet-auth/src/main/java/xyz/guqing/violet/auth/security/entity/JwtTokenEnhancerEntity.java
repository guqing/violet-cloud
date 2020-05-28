package xyz.guqing.violet.auth.security.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-16
 */
@Data
public class JwtTokenEnhancerEntity {
    private Long id;

    private String username;

    private Long groupId;

    private String groupName;

    private String roleId;

    private String roleName;

    private String email;

    private String mobile;

    private Integer gender;

    private Integer isTab;

    private String theme;

    private String avatar;

    private String description;

    private LocalDateTime lastLoginTime;

    private Integer status;
}
