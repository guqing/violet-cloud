package xyz.guqing.violet.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.auth.service.ValidateCodeService;
import xyz.guqing.violet.common.core.exception.ValidateCodeException;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
public class SecurityController {

    private final ValidateCodeService validateCodeService;

    @GetMapping("user")
    public Principal currentUser(Principal principal) {
        return principal;
    }

    @GetMapping("captcha")
    public ResultEntity<String> captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            validateCodeService.create(request, response);
            return ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultEntity.fail();
    }
}
