package xyz.guqing.violet.auth.security.support;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import xyz.guqing.violet.auth.entity.MyUserDetails;

import java.util.HashMap;
import java.util.Map;

/**
 * JWTokenEnhancer
 *
 * @author guqing
 * @date 2020-5-13
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        info.put("jwt-ext", "JWT 扩展信息");

        MyUserDetails myUserDetails = (MyUserDetails)oAuth2Authentication.getPrincipal();
        info.put("id", myUserDetails.getId());
        info.put("username", oAuth2Authentication.getName());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
