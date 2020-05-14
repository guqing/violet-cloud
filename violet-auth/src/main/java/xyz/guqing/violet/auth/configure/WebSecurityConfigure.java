package xyz.guqing.violet.auth.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xyz.guqing.violet.auth.handler.VioletWebLoginFailureHandler;
import xyz.guqing.violet.auth.handler.VioletWebLoginSuccessHandler;
import xyz.guqing.violet.common.core.entity.constant.EndpointConstant;

/**
 * WebSecurityConfig
 *
 * @author guqing
 * @date 2020-5-13
 */
@Order(2)
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
    private final VioletWebLoginSuccessHandler successHandler;
    private final VioletWebLoginFailureHandler failureHandler;

    public WebSecurityConfigure(VioletWebLoginSuccessHandler successHandler,
                                VioletWebLoginFailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(EndpointConstant.LOGIN)
                .loginProcessingUrl(EndpointConstant.LOGIN)
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(EndpointConstant.OAUTH_ALL)
                .permitAll()
                .and()
                .csrf().disable()
                .httpBasic().disable();;
    }

}
