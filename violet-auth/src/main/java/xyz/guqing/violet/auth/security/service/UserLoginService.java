package xyz.guqing.violet.auth.security.service;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.dto.SocialLoginDTO;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.entity.system.UserConnection;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.service.UserConnectionService;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.model.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.model.entity.constant.SocialConstant;
import xyz.guqing.violet.common.core.exception.AuthenticationException;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.NotFoundException;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Service
public class UserLoginService {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final AuthRequestFactory factory;
    private final ResourceOwnerPasswordTokenGranter granter;
    private final JdbcClientDetailsService jdbcClientDetailsService;
    private final UserService userService;
    private final UserConnectionService userConnectionService;
    private final VioletAuthProperties violetAuthProperties;

    public UserLoginService(AuthRequestFactory factory,
                            ResourceOwnerPasswordTokenGranter granter,
                            JdbcClientDetailsService jdbcClientDetailsService,
                            UserService userService,
                            UserConnectionService userConnectionService,
                            VioletAuthProperties violetAuthProperties) {
        this.factory = factory;
        this.granter = granter;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.userService = userService;
        this.userConnectionService = userConnectionService;
        this.violetAuthProperties = violetAuthProperties;
    }

    public SocialLoginDTO resolveLogin(String type, AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest(type);
        AuthResponse response = authRequest.login(callback);
        if (!response.ok()) {
            throw new AuthenticationException("第三方登录失败:" + response.getMsg());
        }

        AuthUser authUser = (AuthUser) response.getData();
        String source = authUser.getSource().name();
        UserConnection userConnection = userConnectionService.getBySourceAndUuid(source, authUser.getUuid());

        SocialLoginDTO socialLoginDTO = new SocialLoginDTO();
        if(Objects.isNull(userConnection)) {
            socialLoginDTO.setIsBind(false);
            socialLoginDTO.setAuthUser(authUser);
            return socialLoginDTO;
        }

        User user = userService.loadUserByUsername(userConnection.getUserName());
        OAuth2AccessToken oauth2AccessToken = getOauth2AccessToken(user);
        socialLoginDTO.setIsBind(true);
        socialLoginDTO.setAccessToken(oauth2AccessToken);
        return socialLoginDTO;
    }

    public AuthRequest getAuthRequest(String type) {
        if (StrUtil.isNotBlank(type)) {
            AuthSource authSource = AuthSource.valueOf(type.toUpperCase());
            return factory.get(authSource);
        } else {
            throw new AuthenticationException(String.format("暂不支持%s第三方登录", type));
        }
    }

    /**
     * 根据用户信息获取token
     * @param user 用户信息
     * @return 认证成功返回OAuth2AccessToken
     * @throws BadRequestException 查询第三方可用ClientDetails出错
     * @throws NotFoundException 获取不到第三方可以用的ClientDetails
     */
    private OAuth2AccessToken getOauth2AccessToken(User user) {
        // setAttribute有效范围是一个request
        HttpServletRequest httpServletRequest = VioletUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);

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
        requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }

}
