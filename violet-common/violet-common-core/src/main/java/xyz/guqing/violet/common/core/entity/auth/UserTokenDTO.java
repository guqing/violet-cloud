package xyz.guqing.violet.common.core.entity.auth;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author guqing
 * @date 2020-05-07
 */
@Data
@Builder
public class UserTokenDTO {
    private OAuth2AccessToken accessToken;
    private String username;
}
