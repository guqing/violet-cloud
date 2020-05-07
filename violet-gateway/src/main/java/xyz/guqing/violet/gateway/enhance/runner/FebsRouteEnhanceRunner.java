package xyz.guqing.violet.gateway.enhance.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import xyz.guqing.violet.gateway.enhance.service.BlackListService;
import xyz.guqing.violet.gateway.enhance.service.RateLimitRuleService;
import xyz.guqing.violet.gateway.enhance.service.RouteEnhanceCacheService;

/**
 * @author MrBird
 */
@RequiredArgsConstructor
public class FebsRouteEnhanceRunner implements ApplicationRunner {

    private final RouteEnhanceCacheService cacheService;
    private final BlackListService blackListService;
    private final RateLimitRuleService rateLimitRuleService;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("已开启网关增强功能：请求日志、黑名单&限流。");
        cacheService.saveAllBlackList(blackListService.findAll());
        cacheService.saveAllRateLimitRules(rateLimitRuleService.findAll());
    }
}
