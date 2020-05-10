package xyz.guqing.violet.common.security.starter.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.guqing.violet.common.security.starter.interceptor.VioletServerProtectInterceptor;

/**
 * @author MrBird
 */
public class VioletCloudSecurityInteceptorConfigure implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor violetServerProtectInterceptor() {
        return new VioletServerProtectInterceptor();
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(violetServerProtectInterceptor());
    }
}
