package xyz.guqing.violet.gateway.enhance.configure;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import xyz.guqing.violet.common.core.entity.constant.FebsConstant;
import xyz.guqing.violet.gateway.enhance.runner.FebsRouteEnhanceRunner;
import xyz.guqing.violet.gateway.enhance.service.BlackListService;
import xyz.guqing.violet.gateway.enhance.service.RateLimitRuleService;
import xyz.guqing.violet.gateway.enhance.service.RouteEnhanceCacheService;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author guqing
 */
@EnableAsync
@Configuration
@EnableReactiveMongoRepositories(basePackages = "xyz.guqing.violet.gateway.enhance.mapper")
@ConditionalOnProperty(name = "violet.gateway.enhance", havingValue = "true")
public class FebsRouteEnhanceConfigure {

    @Bean(FebsConstant.ASYNC_POOL)
    public ThreadPoolTaskExecutor asyncThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("Violet-Gateway-Async-Thread");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public ApplicationRunner febsRoutenEhanceRunner(RouteEnhanceCacheService cacheService,
                                                    BlackListService blackListService,
                                                    RateLimitRuleService rateLimitRuleService) {
        return new FebsRouteEnhanceRunner(cacheService, blackListService, rateLimitRuleService);
    }
}
