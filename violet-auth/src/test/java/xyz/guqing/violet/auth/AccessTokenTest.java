package xyz.guqing.violet.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import xyz.guqing.violet.auth.config.JwtTokenConfig;
import xyz.guqing.violet.auth.config.VioletAuthorizationServerConfig;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.NotFoundException;
import xyz.guqing.violet.common.core.model.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.model.constant.ParamsConstant;
import xyz.guqing.violet.common.core.model.constant.SocialConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-07-15
 */
@SpringBootTest
public class AccessTokenTest {
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;
    @Autowired
    private VioletAuthProperties violetAuthProperties;
    @Autowired
    private TokenGranter granter;
    @Autowired
    private VioletAuthorizationServerConfig violetAuthorizationServerConfig;
    @Test
    void test() {
        String socialLoginClientId = violetAuthProperties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = jdbcClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new BadRequestException("获取第三方登录可用的Client失败");
        }

        if(clientDetails == null) {
            throw new NotFoundException("未找到第三方登录可用的Client");
        }

        Map<String, String> requestParameters = new HashMap<>(3, 1);
        requestParameters.put(ParamsConstant.GRANT_TYPE, SocialConstant.SOCIAL_LOGIN);
        requestParameters.put("username", "guqing");
        requestParameters.put("password", "123456");

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        //OAuth2AccessToken auth2AccessToken = granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);


        AuthorizationServerEndpointsConfigurer endpoint = violetAuthorizationServerConfig.getEndpointsConfigurer();
        OAuth2AccessToken token = endpoint.getTokenGranter().grant(GrantTypeConstant.PASSWORD, tokenRequest);

        System.out.println(token);
    }
}
