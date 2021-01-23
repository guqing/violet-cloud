package xyz.guqing.violet.common.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guqing
 */
@ConfigurationProperties(prefix = "violet.lettuce.redis")
public class LettuceRedisProperties {

    /**
     * 是否开启Lettuce Redis
     */
    private Boolean enable = true;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "LettuceRedisProperties{" +
                "enable=" + enable +
                '}';
    }
}
