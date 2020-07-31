package xyz.guqing.violet.gateway.enhance.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.gateway.enhance.model.entity.RateLimitRule;

/**
 * @author guqing
 * @date 2020-07-31
 */
@Data
public class RateLimitRuleQuery implements InputConverter<RateLimitRule> {
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
     * 状态，0关闭，1开启
     */
    private String status;

    private String createTimeFrom;

    private String createTimeTo;
}
