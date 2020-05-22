package xyz.guqing.violet.auth.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author guqing
 * @date 2020-05-22
 */
@Data
@Component
@ConfigurationProperties(prefix = "violet.auth")
public class VioletAuthProperties {
    private String socialLoginClientId;
    private String redirectUrl;
}
