package xyz.guqing.violet.auth.security.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import xyz.guqing.violet.auth.event.UserLoginEvent;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.auth.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken增强器
 *
 * @author guqing
 * @date 2020-5-13
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User userDetails = (User)oAuth2Authentication.getPrincipal();
        String username = userDetails.getUsername();

        UserInfoDTO userInfo = userService.getUserInfo(username);

        // 发布登录成功事件处理登录
        applicationContext.publishEvent(new UserLoginEvent(this, username));

        Map<String, Object> jwtExt = new HashMap<>(1,1);
        jwtExt.put("userInfo", userInfo);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(jwtExt);
        return oAuth2AccessToken;
    }
}
