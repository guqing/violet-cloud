package xyz.guqing.violet.common.core.model.entity.support;


import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.model.entity.system.Menu;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {
    private String key;
    private String value;
    private String icon;
    private String type;
}
