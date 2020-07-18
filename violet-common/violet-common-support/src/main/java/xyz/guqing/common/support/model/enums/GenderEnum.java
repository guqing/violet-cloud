package xyz.guqing.common.support.model.enums;

/**
 * @author guqing
 * @date 2020-06-03
 */
public enum GenderEnum {
    /**
     * 用户性别，0男，1女，2未知
     */
    MALE(0),
    FEMALE(1),
    UNKNOWN(2);

    private Integer value;

    GenderEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
