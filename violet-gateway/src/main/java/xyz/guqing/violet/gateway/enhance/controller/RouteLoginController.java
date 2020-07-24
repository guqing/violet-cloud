package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.exception.AuthenticationException;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.gateway.enhance.auth.JwtTokenHelper;
import xyz.guqing.violet.gateway.enhance.service.RouteUserService;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("route")
public class RouteLoginController {
    private final JwtTokenHelper tokenHelper;
    private final PasswordEncoder passwordEncoder;
    private final RouteUserService routeUserService;

    @GetMapping("login")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ResultEntity<String>> login(String username, String password) {
        return routeUserService.findByUsername(username)
                .map(u -> {
                    if (u == null) {
                        throw new AuthenticationException("用户不存在");
                    }
                    boolean matches = passwordEncoder.matches(password, u.getPassword());
                    if (!matches) {
                        throw new AuthenticationException("认证失败，用户名或密码错误");
                    }
                    return ResultEntity.ok(tokenHelper.generateToken(u));
                });
    }
}
