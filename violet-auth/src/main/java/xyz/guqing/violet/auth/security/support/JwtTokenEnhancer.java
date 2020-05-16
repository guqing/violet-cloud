package xyz.guqing.violet.auth.security.support;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import xyz.guqing.violet.auth.entity.MyUserDetails;
import xyz.guqing.violet.auth.security.entity.JwtTokenEnhancerEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken增强器
 *
 * @author guqing
 * @date 2020-5-13
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        MyUserDetails myUserDetails = (MyUserDetails)oAuth2Authentication.getPrincipal();

        JwtTokenEnhancerEntity jwtTokenEnhancerEntity = new JwtTokenEnhancerEntity();
        jwtTokenEnhancerEntity.setDescription("JWT 扩展信息");
        jwtTokenEnhancerEntity.setId(myUserDetails.getId());
        jwtTokenEnhancerEntity.setUsername(oAuth2Authentication.getName());

        Map<String, Object> jwtExt = new HashMap<>();
        jwtExt.put("jwt_ext", jwtTokenEnhancerEntity);

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(jwtExt);
        return oAuth2AccessToken;
    }
}
