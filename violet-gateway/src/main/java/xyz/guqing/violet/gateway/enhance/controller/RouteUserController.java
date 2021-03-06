package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.CreateCheck;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.support.UpdateCheck;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteUser;
import xyz.guqing.violet.gateway.enhance.model.params.UserParam;
import xyz.guqing.violet.gateway.enhance.model.params.UserQuery;
import xyz.guqing.violet.gateway.enhance.service.RouteUserService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route/auth/users")
public class RouteUserController {

    private final RouteUserService routeUserService;

    @GetMapping("data")
    public Flux<RouteUser> listUserByPage(PageQuery request, UserQuery userQuery) {
        RouteUser routeUser = userQuery.convertTo();
        return routeUserService.findPages(request, routeUser);
    }

    @GetMapping("count")
    public Mono<Long> countUsers(UserQuery userQuery) {
        RouteUser routeUser = userQuery.convertTo();
        return routeUserService.findCount(routeUser);
    }

    @GetMapping("{username}")
    public Mono<RouteUser> listByUsername(@PathVariable String username) {
        return routeUserService.findByUsername(username);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<RouteUser> createRouteUser(@RequestBody @Validated(CreateCheck.class) UserParam userParam) {
        RouteUser routeUser = userParam.convertTo();
        return routeUserService.create(routeUser);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<RouteUser> updateRouteUser(@RequestBody @Validated(UpdateCheck.class) UserParam userParam) {
        RouteUser routeUser = userParam.convertTo();
        return routeUserService.update(routeUser);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<RouteUser> deleteRouteUser(@RequestBody List<String> ids) {
        return routeUserService.delete(ids);
    }
}
