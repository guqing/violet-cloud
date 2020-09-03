package xyz.guqing.violet.common.security.starter.configure;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Base64Utils;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;
import xyz.guqing.violet.common.security.starter.handler.VioletAccessDeniedHandler;
import xyz.guqing.violet.common.security.starter.handler.VioletAuthExceptionEntryPoint;
import xyz.guqing.violet.common.security.starter.properties.VioletCloudSecurityProperties;

/**
 * @author guqing
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(VioletCloudSecurityProperties.class)
@ConditionalOnProperty(value = "violet.cloud.security.enable", havingValue = "true", matchIfMissing = true)
public class VioletCloudSecurityAutoconfigure {

    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public VioletAccessDeniedHandler accessDeniedHandler() {
        return new VioletAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public VioletAuthExceptionEntryPoint authenticationEntryPoint() {
        return new VioletAuthExceptionEntryPoint();
    }

    @Bean
    public VioletCloudSecurityInteceptorConfigure violetCloudSecurityInteceptorConfigure() {
        return new VioletCloudSecurityInteceptorConfigure();
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            String gatewayToken = new String(Base64Utils.encode(VioletConstant.GATEWAY_TOKEN_VALUE.getBytes()));
            requestTemplate.header(VioletConstant.GATEWAY_TOKEN_HEADER, gatewayToken);
            String authorizationToken = VioletSecurityHelper.getCurrentTokenValue();
            requestTemplate.header(HttpHeaders.AUTHORIZATION, VioletConstant.OAUTH2_TOKEN_TYPE + authorizationToken);
        };
    }
}
