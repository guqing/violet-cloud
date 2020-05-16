package xyz.guqing.violet.auth.security.entity;

import lombok.Data;

/**
 * @author guqing
 * @date 2020-05-16
 */
@Data
public class JwtTokenEnhancerEntity {
    private Long id;
    private String username;
    private String description;
}
