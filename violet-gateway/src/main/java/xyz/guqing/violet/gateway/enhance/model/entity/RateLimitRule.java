package xyz.guqing.violet.gateway.enhance.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author guqing
 */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitRule {
    public static final String METHOD_ALL = "all";

    @Id
    private String id;
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    private String requestMethod;
    /**
     * 限制时间起
     */
    private String limitFrom;
    /**
     * 限制时间止
     */
    private String limitTo;
    /**
     * 次数
     */
    private String count;
    /**
     * 时间周期，单位秒
     */
    private String intervalSec;
    /**
     * 状态，false关闭，true开启
     */
    private Boolean status;
    /**
     * 规则创建时间
     */
    private String createTime;
}
