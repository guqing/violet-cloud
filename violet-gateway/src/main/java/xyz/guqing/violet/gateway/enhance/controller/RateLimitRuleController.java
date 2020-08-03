package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.gateway.enhance.model.entity.RateLimitRule;
import xyz.guqing.violet.gateway.enhance.model.params.RateLimitRuleParam;
import xyz.guqing.violet.gateway.enhance.model.params.RateLimitRuleQuery;
import xyz.guqing.violet.gateway.enhance.service.RateLimitRuleService;

import javax.validation.Valid;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("route/auth/rateLimitRule")
public class RateLimitRuleController {

    private final RateLimitRuleService rateLimitRuleService;

    @GetMapping("data")
    public Flux<RateLimitRule> findUserPages(QueryRequest request, RateLimitRuleQuery rateLimitRuleQuery) {
        RateLimitRule rateLimitRule = rateLimitRuleQuery.convertTo();
        return rateLimitRuleService.findPages(request, rateLimitRule);
    }

    @GetMapping("count")
    public Mono<Long> findUserCount(RateLimitRuleQuery rateLimitRuleQuery) {
        RateLimitRule rateLimitRule = rateLimitRuleQuery.convertTo();
        return rateLimitRuleService.findCount(rateLimitRule);
    }

    @GetMapping("exist")
    public Flux<RateLimitRule> findByRequestUriAndRequestMethod(String requestUri, String requestMethod) {
        return rateLimitRuleService.findByRequestUriAndRequestMethod(requestUri, requestMethod);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<RateLimitRule> createRateLimitRule(@RequestBody @Valid RateLimitRuleParam rateLimitRuleParam) {
        RateLimitRule rateLimitRule = rateLimitRuleParam.convertTo();
        return rateLimitRuleService.create(rateLimitRule);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<RateLimitRule> updateRateLimitRule(@RequestBody @Valid RateLimitRuleParam rateLimitRuleParam) {
        RateLimitRule rateLimitRule = rateLimitRuleParam.convertTo();
        return rateLimitRuleService.update(rateLimitRule);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<RateLimitRule> deleteRateLimitRule(String ids) {
        return rateLimitRuleService.delete(ids);
    }
}
