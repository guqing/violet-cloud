package xyz.guqing.violet.gateway.common.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.gateway.enhance.service.RouteEnhanceService;

/**
 * @author guqing
 */
@Slf4j
@Component
@Order(0)
@RequiredArgsConstructor
public class VioletGatewayRequestFilter implements GlobalFilter {

    private final RouteEnhanceService routeEnhanceService;
    @Value("${violet.gateway.enhance:false}")
    private Boolean routeEhance;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (routeEhance) {
            Mono<Void> balckListResult = routeEnhanceService.filterBlackList(exchange);
            if (balckListResult != null) {
                routeEnhanceService.saveBlockLogs(exchange);
                return balckListResult;
            }
            Mono<Void> rateLimitResult = routeEnhanceService.filterRateLimit(exchange);
            if (rateLimitResult != null) {
                routeEnhanceService.saveRateLimitLogs(exchange);
                return rateLimitResult;
            }
            routeEnhanceService.saveRequestLogs(exchange);
        }

        byte[] token = Base64Utils.encode((VioletConstant.GATEWAY_TOKEN_VALUE).getBytes());
        String[] headerValues = {new String(token)};
        ServerHttpRequest build = exchange.getRequest().mutate().header(VioletConstant.GATEWAY_TOKEN_HEADER, headerValues).build();
        ServerWebExchange newExchange = exchange.mutate().request(build).build();
        return chain.filter(newExchange);
    }
}
