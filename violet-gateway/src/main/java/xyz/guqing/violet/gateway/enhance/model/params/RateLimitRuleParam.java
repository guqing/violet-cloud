package xyz.guqing.violet.gateway.enhance.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.gateway.enhance.model.entity.RateLimitRule;

/**
 * @author guqing
 * @date 2020-08-03
 */
@Data
public class RateLimitRuleParam implements InputConverter<RateLimitRule> {
    /**
     * 请求URI
     */
    private String requestUri;
    /**
     * 请求方法，如果为ALL则表示对所有方法生效
     */
    private String requestMethod;

    private String count;

    private String intervalSec;

    private Boolean status;

    /**
     * 限制时间起
     */
    private String limitFrom;
    /**
     * 限制时间止
     */
    private String limitTo;
}
