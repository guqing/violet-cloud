package xyz.guqing.violet.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;

import java.security.Principal;

/**
 * @author guqing
 * @date 2020-04-30 12:32
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
public class OauthController {
    private final UserService userService;

    @Autowired
    public OauthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login/page")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("/user")
    public ResultEntity<CurrentUser> getUserInfo() {
        String username = VioletSecurityHelper.getCurrentUsername();
        CurrentUser currentUser = userService.loadUserByUsername(username);
        return ResultEntity.ok(currentUser);
    }
}
