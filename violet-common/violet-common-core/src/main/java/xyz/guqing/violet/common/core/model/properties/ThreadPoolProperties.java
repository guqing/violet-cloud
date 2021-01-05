package xyz.guqing.violet.common.core.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guqing
 * @date 2020-04-21 10:02
 */
@Data
@ConfigurationProperties(prefix = "violet.thread.pool")
public class ThreadPoolProperties {
    private Integer corePoolSize = 8;
    private Integer maximumPoolSize = 20;
    private Integer queueSize = 100;
    private Integer keepAliveTime = 30;
}
