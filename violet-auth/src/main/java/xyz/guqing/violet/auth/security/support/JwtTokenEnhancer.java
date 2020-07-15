package xyz.guqing.violet.auth.security.support;

import org.springframework.beans.BeanUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.common.core.model.bo.MyUserDetails;

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

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        // 拷贝属性
        BeanUtils.copyProperties(myUserDetails, userInfoDTO);

        Map<String, Object> jwtExt = new HashMap<>(1,1);
        jwtExt.put("userInfo", userInfoDTO);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(jwtExt);
        return oAuth2AccessToken;
    }
}
