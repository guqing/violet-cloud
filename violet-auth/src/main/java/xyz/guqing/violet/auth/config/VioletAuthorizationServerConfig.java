package xyz.guqing.violet.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import xyz.guqing.violet.auth.security.service.MyJdbcClientDetailsService;
import xyz.guqing.violet.auth.security.service.UserDetailsServiceImpl;
import xyz.guqing.violet.auth.security.support.JwtTokenEnhancer;
import xyz.guqing.violet.auth.translator.VioletWebResponseExceptionTranslator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guqing
 * @date 2020-5-13
 */
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class VioletAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthenticationManager authenticationManager;

    private final DataSource dataSource;

    private final JwtTokenStore jwtTokenStore;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    private final JwtTokenEnhancer jwtTokenEnhancer;

    private final VioletWebResponseExceptionTranslator webResponseExceptionTranslator;

    /**
     * 用于保存配置在重载方法configure中为其赋值
     */
    private AuthorizationServerEndpointsConfigurer endpointsConfigurer;

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        // jwt 增强模式
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);

        /*
         * jwt token 方式
         * 可修改tokenStore为RedisTokenStore替换存储方式
         */
        endpoints.tokenStore(jwtTokenStore)
                .userDetailsService(userDetailsServiceImpl)
                // 支持 password 模式
                .authenticationManager(authenticationManager)
                .tokenEnhancer(enhancerChain)
                .accessTokenConverter(jwtAccessTokenConverter)
                .exceptionTranslator(webResponseExceptionTranslator);;
        this.endpointsConfigurer = endpoints;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();
        security.checkTokenAccess("isAuthenticated()");
        security.tokenKeyAccess("isAuthenticated()");
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new MyJdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();

        tokenServices.setTokenStore(jwtTokenStore);
        tokenServices.setTokenEnhancer(jwtTokenEnhancer);

        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(jdbcClientDetailsService());
        return tokenServices;
    }

    @Bean
    public ResourceOwnerPasswordTokenGranter resourceOwnerPasswordTokenGranter(AuthenticationManager authenticationManager, OAuth2RequestFactory oAuth2RequestFactory) {
        DefaultTokenServices defaultTokenServices = defaultTokenServices();
        return new ResourceOwnerPasswordTokenGranter(authenticationManager, defaultTokenServices, jdbcClientDetailsService(), oAuth2RequestFactory);
    }

    @Bean
    public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
        return new DefaultOAuth2RequestFactory(jdbcClientDetailsService());
    }

    /**
     * 获取 EndpointsConfigurer,后续用于手动生成jwt格式的token，否则只能生成普通token
     *
     * @return 返回EndpointsConfigurer
     */
    public AuthorizationServerEndpointsConfigurer getEndpointsConfigurer() {
        return endpointsConfigurer;
    }
}