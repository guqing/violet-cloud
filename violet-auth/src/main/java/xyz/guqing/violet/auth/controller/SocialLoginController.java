package xyz.guqing.violet.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.guqing.violet.auth.model.dto.SocialLoginDTO;
import xyz.guqing.violet.auth.model.params.BindUserParam;
import xyz.guqing.violet.auth.model.properties.VioletAuthProperties;
import xyz.guqing.violet.auth.security.service.UserLoginService;
import xyz.guqing.violet.common.core.model.constant.StringConstant;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Slf4j
@Controller
@RequestMapping("social")
public class SocialLoginController {
    private static final String TYPE_BIND = "bind";
    private final UserLoginService userLoginService;
    private final VioletAuthProperties authProperties;
    private final AuthRequestFactory authRequestFactory;

    public SocialLoginController(UserLoginService userLoginService,
                                 VioletAuthProperties authProperties,
                                 AuthRequestFactory authRequestFactory) {
        this.userLoginService = userLoginService;
        this.authProperties = authProperties;
        this.authRequestFactory = authRequestFactory;
    }

    @ResponseBody
    @GetMapping("/list")
    public ResultEntity<List<String>> connections() {
        List<String> oauthList = authRequestFactory.oauthList();
        return ResultEntity.ok(oauthList);
    }

    @GetMapping("/login/{oauthType}/{type}")
    public void login(@PathVariable String oauthType,
                      @PathVariable String type,
                      HttpServletResponse response) throws IOException {
        AuthRequest authRequest = userLoginService.getAuthRequest(oauthType);
        String state = AuthStateUtils.createState() + StringConstant.DOUBLE_COLON + type;
        response.sendRedirect(authRequest.authorize(state));
    }

    @RequestMapping("/{oauthType}/callback")
    public ModelAndView login(@PathVariable String oauthType,
                              String state,
                              AuthCallback callback,
                              ModelAndView modelAndView) {
        String type = StringUtils.substringAfterLast(state, StringConstant.DOUBLE_COLON);

        SocialLoginDTO socialLoginDTO;
        if (StringUtils.equals(type, TYPE_BIND)) {
            // 帐号绑定
            socialLoginDTO = userLoginService.resolveBind(oauthType, callback);
        } else {
            // 登录
            socialLoginDTO = userLoginService.resolveLogin(oauthType, callback);
        }

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

    /**
     * 绑定
     *
     * @param authUser authUser
     */
    @ResponseBody
    @PostMapping("/bind")
    public void bind(@RequestBody AuthUser authUser) {
        log.debug("获取系统用户信息:{}", JSONObject.toJSONString(VioletSecurityHelper.getCurrentUser()));
        String username = VioletSecurityHelper.getCurrentUsername();
        this.userLoginService.bind(username, authUser);
    }
}
