package xyz.guqing.violet.auth.controller;

import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.config.AuthSource;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.security.service.UserLoginService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
@Controller
@RequestMapping("social")
public class SocialLoginController {
    private final AuthRequestFactory factory;
    private final UserLoginService userLoginService;
    private final VioletAuthProperties authProperties;

    public SocialLoginController(AuthRequestFactory factory,
                                 UserLoginService userLoginService,
                                 VioletAuthProperties authProperties) {
        this.factory = factory;
        this.userLoginService = userLoginService;
        this.authProperties = authProperties;
    }
    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthSource authSource = AuthSource.valueOf(type);
        AuthRequest authRequest = factory.get(authSource);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/{type}/callback")
    public ModelAndView login(@PathVariable String type, AuthCallback callback, ModelAndView modelAndView) {
        AuthSource authSource = AuthSource.valueOf(type);
        AuthRequest authRequest = factory.get(authSource);
        AuthResponse response = authRequest.login(callback);

        OAuth2AccessToken oAuth2AccessToken = userLoginService.resolveLogin(response);

        modelAndView.addObject("response", oAuth2AccessToken);
        modelAndView.addObject("redirectUrl", authProperties.getRedirectUrl());
        modelAndView.setViewName("socialLoginResult");
        return modelAndView;
    }
}
