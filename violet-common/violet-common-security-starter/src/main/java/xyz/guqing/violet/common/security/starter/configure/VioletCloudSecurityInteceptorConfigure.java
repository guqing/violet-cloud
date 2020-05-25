package xyz.guqing.violet.common.security.starter.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.guqing.violet.common.security.starter.interceptor.VioletServerProtectInterceptor;
import xyz.guqing.violet.common.security.starter.properties.VioletCloudSecurityProperties;

/**
 * @author guqing
 */
public class VioletCloudSecurityInteceptorConfigure implements WebMvcConfigurer {
    private VioletCloudSecurityProperties properties;

    @Autowired
    public void setProperties(VioletCloudSecurityProperties properties) {
        this.properties = properties;
    }

    @Bean
    public HandlerInterceptor violetServerProtectInterceptor() {
        VioletServerProtectInterceptor violetServerProtectInterceptor = new VioletServerProtectInterceptor();
        violetServerProtectInterceptor.setProperties(properties);
        return violetServerProtectInterceptor;
    }

    @Override
    @SuppressWarnings("all")
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(violetServerProtectInterceptor());
    }
}
