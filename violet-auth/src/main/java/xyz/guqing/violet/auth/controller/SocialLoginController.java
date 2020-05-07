package xyz.guqing.violet.auth.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.auth.entity.BindUser;
import xyz.guqing.violet.auth.entity.UserConnection;
import xyz.guqing.violet.auth.service.SocialLoginService;
import xyz.guqing.violet.common.core.entity.auth.UserTokenDTO;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

/**
 * @author guqing
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("social")
public class SocialLoginController {

    private static final String TYPE_LOGIN = "login";
    private static final String TYPE_BIND = "bind";

    private final SocialLoginService socialLoginService;
    @Value("${febs.frontUrl}")
    private String frontUrl;


    /**
     * 登录
     *
     * @param oauthType 第三方登录类型
     * @param response  response
     */
    @ResponseBody
    @GetMapping("/login/{oauthType}/{type}")
    public void renderAuth(@PathVariable String oauthType, @PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = socialLoginService.renderAuth(oauthType);
        response.sendRedirect(authRequest.authorize(oauthType + "::" + AuthStateUtils.createState()) + "::" + type);
    }

    /**
     * 登录成功后的回调
     *
     * @param oauthType 第三方登录类型
     * @param callback  携带返回的信息
     * @return String
     */
    @GetMapping("/{oauthType}/callback")
    public ResultEntity<String> login(@PathVariable String oauthType, AuthCallback callback, String state, Model model) {
        String type = StringUtils.substringAfterLast(state, "::");
        if (StringUtils.equals(type, TYPE_BIND)) {
            Object object = socialLoginService.resolveBind(oauthType, callback);
            System.out.println("callback TYPE_BIND---->" + JSONObject.toJSONString(object));
        } else {
            UserTokenDTO userTokenDTO = socialLoginService.resolveLogin(oauthType, callback);
            System.out.println("callback TYPE_BIND---->" + JSONObject.toJSONString(userTokenDTO));
        }
//        model.addAttribute("frontUrl", frontUrl);
        return ResultEntity.ok();
    }

    /**
     * 绑定并登录
     *
     * @param bindUser bindUser
     * @param authUser authUser
     * @return FebsResponse
     */
    @ResponseBody
    @PostMapping("bind/login")
    public ResultEntity<OAuth2AccessToken> bindLogin(@Valid BindUser bindUser, AuthUser authUser) {
        OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.bindLogin(bindUser, authUser);
        return ResultEntity.ok(oAuth2AccessToken);
    }

    /**
     * 注册并登录
     *
     * @param registUser registUser
     * @param authUser   authUser
     * @return FebsResponse
     */
    @ResponseBody
    @PostMapping("sign/login")
    public ResultEntity<OAuth2AccessToken> signLogin(@Valid BindUser registUser, AuthUser authUser) {
        OAuth2AccessToken oAuth2AccessToken = this.socialLoginService.signLogin(registUser, authUser);
        return ResultEntity.ok(oAuth2AccessToken);
    }

    /**
     * 绑定
     *
     * @param bindUser bindUser
     * @param authUser authUser
     */
    @ResponseBody
    @PostMapping("bind")
    public void bind(BindUser bindUser, AuthUser authUser) {
        this.socialLoginService.bind(bindUser, authUser);
    }

    /**
     * 解绑
     *
     * @param bindUser  bindUser
     * @param oauthType oauthType
     */
    @ResponseBody
    @DeleteMapping("unbind")
    public void unbind(BindUser bindUser, String oauthType) {
        this.socialLoginService.unbind(bindUser, oauthType);
    }

    /**
     * 根据用户名获取绑定关系
     *
     * @param username 用户名
     * @return FebsResponse
     */
    @ResponseBody
    @GetMapping("connections/{username}")
    public ResultEntity<List<UserConnection>> findUserConnections(@NotBlank(message = "{required}") @PathVariable String username) {
        List<UserConnection> userConnections = this.socialLoginService.findUserConnections(username);
        return ResultEntity.ok(userConnections);
    }
}
