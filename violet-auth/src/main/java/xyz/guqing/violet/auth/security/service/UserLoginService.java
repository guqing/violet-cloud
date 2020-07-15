package xyz.guqing.violet.auth.security.service;

import cn.hutool.core.util.StrUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.violet.auth.model.dto.SocialLoginDTO;
import xyz.guqing.violet.auth.model.params.BindUserParam;
import xyz.guqing.violet.auth.service.UserRoleService;
import xyz.guqing.violet.common.core.exception.*;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.entity.system.UserConnection;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.service.UserConnectionService;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.model.constant.ParamsConstant;
import xyz.guqing.violet.common.core.model.constant.SocialConstant;
import xyz.guqing.violet.common.core.model.entity.system.UserRole;
import xyz.guqing.violet.common.core.model.enums.GenderEnum;
import xyz.guqing.violet.common.core.model.enums.UserStatusEnum;
import xyz.guqing.violet.common.core.utils.RedisUtils;
import xyz.guqing.violet.common.core.utils.VioletUtil;
import xyz.guqing.violet.common.redis.service.RedisService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final UserRoleService userRoleService;

    public UserLoginService(AuthRequestFactory factory,
                            ResourceOwnerPasswordTokenGranter granter,
                            JdbcClientDetailsService jdbcClientDetailsService,
                            UserService userService,
                            UserConnectionService userConnectionService,
                            VioletAuthProperties violetAuthProperties,
                            PasswordEncoder passwordEncoder,
                            RedisService redisService,
                            UserRoleService userRoleService) {
        this.factory = factory;
        this.granter = granter;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.userService = userService;
        this.userConnectionService = userConnectionService;
        this.violetAuthProperties = violetAuthProperties;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.userRoleService = userRoleService;
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

        User user = userService.getById(userConnection.getUserId());
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

    /**
     * 注册并登录
     *
     * @param registerUser 注册用户
     * @param authUser   第三方平台对象
     * @return 注册并登录成功返回令牌对象
     */
    public OAuth2AccessToken socialSignLogin(BindUserParam registerUser, AuthUser authUser) {
        // 校验验证码
        boolean checkResult = checkEmailCaptcha(registerUser.getEmail(), registerUser.getCaptcha());
        if(!checkResult) {
            throw new BadArgumentException("验证码错误");
        }

        Optional<User> optionalUser = userService.getByEmail(registerUser.getEmail());
        if(optionalUser.isPresent()) {
            // 用户存在，则抛出异常
            throw new AlreadyExistsException("该用户已经存在");
        }

        String encryptPassword = passwordEncoder.encode(registerUser.getPassword());
        // 注册
        User user = registerUser(registerUser.getEmail(), encryptPassword);
        user.setNickname(authUser.getNickname());
        user.setDescription(authUser.getRemark());
        user.setAvatar(authUser.getAvatar());
        // 保存第三方绑定帐号
        userConnectionService.create(user.getId(), authUser);
        return getOauth2AccessToken(user);
    }

    /**
     * 注册用户
     *
     * @param email email
     * @param password password
     * @return SystemUser SystemUser
     */
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(email);
        user.setUsername(generateUsername());
        user.setPassword(password);
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setGender(GenderEnum.UNKNOWN.getValue());
        user.setAvatar("");
        user.setDescription("该用户很懒，什么也没有留下");
        user.setCreateTime(LocalDateTime.now());
        user.setModifyTime(LocalDateTime.now());
        userService.save(user);

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        // 注册用户角色 ID
        userRole.setRoleId(VioletConstant.REGISTER_ROLE_ID);
        userRoleService.save(userRole);
        return user;
    }

    /**
     * 校验一次性邮箱验证码正确性
     * @param email 邮箱地址
     * @param captcha 验证码
     * @return 如果校验正确返回{@code true},否则返回{@code false}
     */
    private boolean checkEmailCaptcha(String email, String captcha) {
        Object value = redisService.get(VioletConstant.CAPTCHA_PREFIX+email);
        return value != null && value.equals(captcha);
    }

    private String generateUsername() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }
}
