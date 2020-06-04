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
import xyz.guqing.violet.common.core.model.entity.constant.StringConstant;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

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
    public ResultEntity<UserInfoDTO> getUserInfo() {
        String username = VioletSecurityHelper.getCurrentUsername();
        return ResultEntity.ok(userService.getUserInfo(username));
    }

}
