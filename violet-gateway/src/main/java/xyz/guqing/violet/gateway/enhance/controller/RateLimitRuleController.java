package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.gateway.enhance.model.entity.RateLimitRule;
import xyz.guqing.violet.gateway.enhance.model.params.RateLimitRuleParam;
import xyz.guqing.violet.gateway.enhance.model.params.RateLimitRuleQuery;
import xyz.guqing.violet.gateway.enhance.service.RateLimitRuleService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route/auth/rate/limit/rules")
public class RateLimitRuleController {

    private final RateLimitRuleService rateLimitRuleService;

    @GetMapping("data")
    public Flux<RateLimitRule> findUserPages(PageQuery request, RateLimitRuleQuery rateLimitRuleQuery) {
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

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public Mono<RateLimitRule> updateRateLimitRule(@PathVariable String id,
                                                   @RequestBody @Valid RateLimitRuleParam rateLimitRuleParam) {
        return rateLimitRuleService.getById(id)
                .flatMap(rateLimitRuleToUpdate -> {
                    rateLimitRuleParam.update(rateLimitRuleToUpdate);
                    return rateLimitRuleService.update(rateLimitRuleToUpdate);
                });
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<RateLimitRule> deleteRateLimitRule(@RequestBody List<String> ids) {
        return rateLimitRuleService.delete(ids);
    }
}
