package xyz.guqing.violet.auth.security.service;

import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.User;
import xyz.guqing.violet.auth.model.entity.UserConnection;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.service.UserConnectionService;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.entity.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.entity.constant.SocialConstant;
import xyz.guqing.violet.common.core.exception.AuthenticationException;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Service
public class UserLoginService {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final ResourceOwnerPasswordTokenGranter granter;
    private final JdbcClientDetailsService jdbcClientDetailsService;
    private final UserService userService;
    private final UserConnectionService userConnectionService;
    private final VioletAuthProperties violetAuthProperties;

    public UserLoginService(ResourceOwnerPasswordTokenGranter granter,
                            JdbcClientDetailsService jdbcClientDetailsService,
                            UserService userService,
                            UserConnectionService userConnectionService,
                            VioletAuthProperties violetAuthProperties) {
        this.granter = granter;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.userService = userService;
        this.userConnectionService = userConnectionService;
        this.violetAuthProperties = violetAuthProperties;
    }

    public OAuth2AccessToken resolveLogin(AuthResponse response) {
        if (!response.ok()) {
            throw new AuthenticationException("第三方登录失败:" + response.getMsg());
        }

        AuthUser authUser = (AuthUser) response.getData();
        String source = authUser.getSource().name();
        UserConnection userConnection = userConnectionService.getBySourceAndUuid(source, authUser.getUuid());

        User user = userService.getByUsername(userConnection.getUserName());
        return getOauth2AccessToken(user);
    }

    /**
     * 根据用户信息获取token
     * @param user 用户信息
     * @return 认证成功返回OAuth2AccessToken
     * @throws BadRequestException 查询第三方可用ClientDetails出错
     * @throws NotFoundException 获取不到第三方可以用的ClientDetails
     */
    public OAuth2AccessToken getOauth2AccessToken(User user) {
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

        Map<String, String> requestParameters = new HashMap<>(5, 1);
        requestParameters.put(ParamsConstant.GRANT_TYPE, SocialConstant.SOCIAL_LOGIN);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, user.getPassword());

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }


}
