package xyz.guqing.violet.common.security.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.common.core.model.constant.EndpointConstant;

/**
 * @author guqing
 */
@Data
@Component
@ConfigurationProperties(prefix = "violet.cloud.security")
public class VioletCloudSecurityProperties {
    /**
     * 是否开启安全配置
     */
    private Boolean enable;
    /**
     * 配置需要认证的uri，默认为所有/**
     */
    private String authUri = EndpointConstant.ALL;
    /**
     * 免认证资源路径，支持通配符
     * 多个值时使用逗号分隔
     */
    private String anonUris;

    /**
     * 是否只能通过网关获取资源
     */
    private Boolean onlyFetchByGateway = Boolean.TRUE;
}
