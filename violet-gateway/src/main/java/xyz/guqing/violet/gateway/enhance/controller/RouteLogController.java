package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteLog;
import xyz.guqing.violet.gateway.enhance.model.params.RouteLogQuery;
import xyz.guqing.violet.gateway.enhance.service.RouteLogService;

import java.util.List;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route/auth/logs")
public class RouteLogController {

    private final RouteLogService routeLogService;

    @GetMapping("data")
    public Flux<RouteLog> listRouteLogsByPage(PageQuery request, RouteLogQuery routeLogQuery) {
        return routeLogService.findPages(request, routeLogQuery);
    }

    @GetMapping("count")
    public Mono<Long> countRouteLogs(RouteLogQuery routeLogQuery) {
        return routeLogService.findCount(routeLogQuery);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<RouteLog> deleteRouteLogs(@RequestBody List<String> ids) {
        return routeLogService.delete(ids);
    }
}
