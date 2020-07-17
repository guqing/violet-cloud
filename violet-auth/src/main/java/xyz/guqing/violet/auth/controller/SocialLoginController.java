package xyz.guqing.violet.auth.controller;

import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.guqing.violet.auth.model.dto.SocialLoginDTO;
import xyz.guqing.violet.auth.model.params.BindUserParam;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.security.service.UserLoginService;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
@Controller
@RequestMapping("social")
public class SocialLoginController {
    private final UserLoginService userLoginService;
    private final VioletAuthProperties authProperties;

    public SocialLoginController(UserLoginService userLoginService,
                                 VioletAuthProperties authProperties) {
        this.userLoginService = userLoginService;
        this.authProperties = authProperties;
    }

    @GetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = userLoginService.getAuthRequest(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/{type}/callback")
    public ModelAndView login(@PathVariable String type, AuthCallback callback, ModelAndView modelAndView) {
        SocialLoginDTO socialLoginDTO = userLoginService.resolveLogin(type, callback);

        modelAndView.addObject("response", socialLoginDTO);
        modelAndView.addObject("redirectUrl", authProperties.getRedirectUrl());
        modelAndView.setViewName("socialLoginResult");
        return modelAndView;
    }

    /**
     * 注册并登录
     *
     * @param registerUser register user
     * @return ResultEntity
     */
    @ResponseBody
    @PostMapping("sign/login")
    public ResultEntity<OAuth2AccessToken> signLogin(@RequestBody @Valid BindUserParam registerUser) {
        OAuth2AccessToken oAuth2AccessToken = this.userLoginService.socialSignLogin(registerUser);
        return ResultEntity.ok(oAuth2AccessToken);
    }
}
