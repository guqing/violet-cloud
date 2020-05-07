package xyz.guqing.violet.common.core.entity.constant;

/**
 * 正则常量
 *
 * @author MrBird
 */
public interface RegexpConstant {

    /**
     * 简单手机号正则（这里只是简单校验是否为 11位，实际规则更复杂）
     */
    String MOBILE_REG = "[1]\\d{10}";

}
