package xyz.guqing.violet.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.entity.BindUser;
import xyz.guqing.violet.auth.entity.UserConnection;
import xyz.guqing.violet.auth.manager.UserManager;
import xyz.guqing.violet.auth.properties.FebsAuthProperties;
import xyz.guqing.violet.auth.service.SocialLoginService;
import xyz.guqing.violet.auth.service.UserConnectionService;
import xyz.guqing.violet.common.core.entity.auth.UserTokenDTO;
import xyz.guqing.violet.common.core.entity.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.entity.constant.SocialConstant;
import xyz.guqing.violet.common.core.entity.system.SystemUser;
import xyz.guqing.violet.common.core.exception.*;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.FebsUtil;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
public class SocialLoginServiceImpl implements SocialLoginService {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private static final String NOT_BIND = "not_bind";

    private final UserManager userManager;
    private final AuthRequestFactory factory;
    private final FebsAuthProperties properties;
    private final PasswordEncoder passwordEncoder;
    private final UserConnectionService userConnectionService;
    private final ResourceOwnerPasswordTokenGranter granter;
    private final RedisClientDetailsService redisClientDetailsService;

    @Override
    public AuthRequest renderAuth(String oauthType) {
        return factory.get(getAuthSource(oauthType));
    }

    @Override
    public Object resolveBind(String oauthType, AuthCallback callback){
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));
        if(!response.ok()) {
            throw new BindSocialAccountException(String.format("第三方登录失败，%s", response.getMsg()));
        }
        return response.getData();
    }

    @Override
    public UserTokenDTO resolveLogin(String oauthType, AuthCallback callback){
        AuthRequest authRequest = factory.get(getAuthSource(oauthType));
        AuthResponse<?> response = authRequest.login(resolveAuthCallback(callback));

        if (!response.ok()) {
            throw new AuthenticationException(String.format("第三方登录失败，%s", response.getMsg()));

        }

        AuthUser authUser = (AuthUser) response.getData();
        UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
        if (userConnection == null) {
            throw new AuthenticationException("帐号未绑定");
        }

        SystemUser user = userManager.findByName(userConnection.getUserName());
        if (user == null) {
            throw new NotFoundException("系统中未找到与第三方账号对应的账户");
        }
        OAuth2AccessToken oAuth2AccessToken = getOauth2AccessToken(user);
        return UserTokenDTO.builder().accessToken(oAuth2AccessToken).username(user.getUsername()).build();
    }

    @Override
    public OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser) {
        SystemUser systemUser = userManager.findByName(bindUser.getBindUsername());
        if (systemUser == null || !passwordEncoder.matches(bindUser.getBindPassword(), systemUser.getPassword())) {
            throw new AuthenticationException("绑定系统账号失败，用户名或密码错误！");
        }
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser) {
        SystemUser user = this.userManager.findByName(registUser.getBindUsername());
        if (user != null) {
            throw new AlreadyExistsException("该用户名已存在！");
        }
        String encryptPassword = passwordEncoder.encode(registUser.getBindPassword());
        SystemUser systemUser = this.userManager.registUser(registUser.getBindUsername(), encryptPassword);
        this.createConnection(systemUser, authUser);
        return this.getOauth2AccessToken(systemUser);
    }

    @Override
    public void bind(BindUser bindUser, AuthUser authUser) {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            UserConnection userConnection = userConnectionService.selectByCondition(authUser.getSource().toString(), authUser.getUuid());
            if (userConnection != null) {
                throw new BindSocialAccountException("绑定失败，该第三方账号已绑定" + userConnection.getUserName() + "系统账户");
            }
            SystemUser systemUser = new SystemUser();
            systemUser.setUsername(username);
            this.createConnection(systemUser, authUser);
        } else {
            throw new BindSocialAccountException("绑定失败，您无权绑定别人的账号");
        }
    }

    @Override
    public void unbind(BindUser bindUser, String oauthType) {
        String username = bindUser.getBindUsername();
        if (isCurrentUser(username)) {
            this.userConnectionService.deleteByCondition(username, oauthType);
        } else {
            throw new BindSocialAccountException("解绑失败，您无权解绑别人的账号");
        }
    }

    @Override
    public List<UserConnection> findUserConnections(String username) {
        return this.userConnectionService.selectByCondition(username);
    }

    private void createConnection(SystemUser systemUser, AuthUser authUser) {
        UserConnection userConnection = new UserConnection();
        userConnection.setUserName(systemUser.getUsername());
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setImageUrl(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());
        this.userConnectionService.createUserConnection(userConnection);
    }

    private AuthCallback resolveAuthCallback(AuthCallback callback) {
        int stateLength = 3;
        String state = callback.getState();
        String[] strings = StringUtils.splitByWholeSeparatorPreserveAllTokens(state, "::");
        if (strings.length == stateLength) {
            callback.setState(strings[0] + "::" + strings[1]);
        }
        return callback;
    }

    private AuthSource getAuthSource(String type) {
        if (StrUtil.isNotBlank(type)) {
            return AuthSource.valueOf(type.toUpperCase());
        } else {
            throw new ForbiddenException(String.format("暂不支持%s第三方登录", type));
        }
    }

    private boolean isCurrentUser(String username) {
        String currentUsername = VioletSecurityHelper.getCurrentUsername();
        return StringUtils.equalsIgnoreCase(username, currentUsername);
    }

    private OAuth2AccessToken getOauth2AccessToken(SystemUser user) {
        final HttpServletRequest httpServletRequest = FebsUtil.getHttpServletRequest();
        httpServletRequest.setAttribute(ParamsConstant.LOGIN_TYPE, SocialConstant.SOCIAL_LOGIN);
        String socialLoginClientId = properties.getSocialLoginClientId();
        ClientDetails clientDetails = null;
        try {
            clientDetails = redisClientDetailsService.loadClientByClientId(socialLoginClientId);
        } catch (Exception e) {
            throw new BadRequestException("获取第三方登录可用的Client失败");
        }
        if (clientDetails == null) {
            throw new NotFoundException("未找到第三方登录可用的Client");
        }
        Map<String, String> requestParameters = new HashMap<>(5);
        requestParameters.put(ParamsConstant.GRANT_TYPE, GrantTypeConstant.PASSWORD);
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, SocialConstant.SOCIAL_LOGIN_PASSWORD);

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }
}
