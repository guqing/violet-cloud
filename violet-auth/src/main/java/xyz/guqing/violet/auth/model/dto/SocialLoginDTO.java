package xyz.guqing.violet.auth.model.dto;

import lombok.Data;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @author guqing
 * @date 2020-05-26
 */
@Data
public class SocialLoginDTO {
    private OAuth2AccessToken accessToken;
    private AuthUser authUser;
    private Boolean isBind;
}
