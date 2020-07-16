package xyz.guqing.violet.app.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.guqing.violet.app.admin.converter.StringToLocalDateConverter;
import xyz.guqing.violet.app.admin.converter.StringToLocalDateTimeConverter;

/**
 * @author guqing
 * @date 2020-07-16
 */
@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
        registry.addConverter(new StringToLocalDateTimeConverter());
    }
}
