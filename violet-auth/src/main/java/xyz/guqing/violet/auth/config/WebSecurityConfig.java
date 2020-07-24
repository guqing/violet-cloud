package xyz.guqing.violet.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import xyz.guqing.violet.auth.handler.VioletAuthenticationFailureHandler;
import xyz.guqing.violet.auth.handler.VioletAuthenticationSuccessHandler;
import xyz.guqing.violet.common.core.model.constant.EndpointConstant;

/**
 * WebSecurityConfig
 *
 * @author guqing
 * @date 2020-5-13
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final VioletAuthenticationFailureHandler authenticationFailureHandler;
    private final VioletAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers(EndpointConstant.OAUTH_ALL).permitAll();
    }


}
