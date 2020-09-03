package xyz.guqing.violet.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.guqing.violet.common.security.starter.handler.VioletAccessDeniedHandler;
import xyz.guqing.violet.common.security.starter.handler.VioletAuthExceptionEntryPoint;

/**
 * @author guqing
 * @date 2020-09-03
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
