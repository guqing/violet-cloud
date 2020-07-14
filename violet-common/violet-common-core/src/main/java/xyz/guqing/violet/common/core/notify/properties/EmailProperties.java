package xyz.guqing.violet.common.core.notify.properties;

import xyz.guqing.violet.common.core.model.enums.PropertyEnum;

/**
 * Email properties.
 *
 * @author johnniang
 * @date 3/27/19
 */
public enum EmailProperties implements PropertyEnum {

    /**
     * 邮件配置枚举
     */
    HOST("email_host", String.class, ""),

    PROTOCOL("email_protocol", String.class, "smtp"),

    SSL_PORT("email_ssl_port", Integer.class, "465"),

    USERNAME("email_username", String.class, ""),

    PASSWORD("email_password", String.class, ""),

    FROM_NAME("email_from_name", String.class, ""),

    ENABLED("email_enabled", Boolean.class, "false");

    private final String value;

    private final Class<?> type;

    private final String defaultValue;

    EmailProperties(String value, Class<?> type, String defaultValue) {
        this.defaultValue = defaultValue;
        if (!PropertyEnum.isSupportedType(type)) {
            throw new IllegalArgumentException("Unsupported blog property type: " + type);
        }

        this.value = value;
        this.type = type;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
