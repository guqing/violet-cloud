package xyz.guqing.violet.common.core.model.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统设置选项
 * @author guqing
 * @date 2020-07-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SettingOption extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private String groupName;
    private String optionKey;
    private String optionValue;
}
