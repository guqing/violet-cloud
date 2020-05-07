package xyz.guqing.violet.auth.service;

import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import xyz.guqing.violet.auth.entity.BindUser;
import xyz.guqing.violet.auth.entity.UserConnection;
import xyz.guqing.violet.common.core.entity.auth.UserTokenDTO;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.List;

/**
 * @author guqing
 */
public interface SocialLoginService {

    /**
     * 解析第三方登录请求
     *
     * @param oauthType 第三方平台类型
     * @return AuthRequest
     */
    AuthRequest renderAuth(String oauthType);

    /**
     * 处理第三方登录（绑定页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     */
    Object resolveBind(String oauthType, AuthCallback callback);

    /**
     * 处理第三方登录（登录页面）
     *
     * @param oauthType 第三方平台类型
     * @param callback  回调
     * @return FebsResponse
     */
    UserTokenDTO resolveLogin(String oauthType, AuthCallback callback);

    /**
     * 绑定并登录
     *
     * @param bindUser 绑定用户
     * @param authUser 第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     */
    OAuth2AccessToken bindLogin(BindUser bindUser, AuthUser authUser);

    /**
     * 注册并登录
     *
     * @param registUser 注册用户
     * @param authUser   第三方平台对象
     * @return OAuth2AccessToken 令牌对象
     */
    OAuth2AccessToken signLogin(BindUser registUser, AuthUser authUser);

    /**
     * 绑定
     *
     * @param bindUser 绑定对象
     * @param authUser 第三方平台对象
     */
    void bind(BindUser bindUser, AuthUser authUser);

    /**
     * 解绑
     *
     * @param bindUser  绑定对象
     * @param oauthType 第三方平台对象
     */
    void unbind(BindUser bindUser, String oauthType);

    /**
     * 根据用户名获取绑定关系
     *
     * @param username 用户名
     * @return 绑定关系集合
     */
    List<UserConnection> findUserConnections(String username);
}
