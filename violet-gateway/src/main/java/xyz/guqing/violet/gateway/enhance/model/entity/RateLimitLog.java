package xyz.guqing.violet.gateway.enhance.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.redis.core.RedisHash;

/**
 * @author guqing
 */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("rate_limit_log")
public class RateLimitLog {

    @Id
    private String id;
    /**
     * 被拦截请求IP
     */
    private String ip;
    /**
     * 被拦截请求URI
     */
    private String requestUri;
    /**
     * 被拦截请求方法
     */
    private String requestMethod;
    /**
     * IP对应地址
     */
    private String location;
    /**
     * 拦截时间点
     */
    private String createTime;

    @Transient
    private String createTimeFrom;
    @Transient
    private String createTimeTo;
}
