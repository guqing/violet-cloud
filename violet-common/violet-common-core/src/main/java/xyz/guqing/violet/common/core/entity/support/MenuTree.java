package xyz.guqing.violet.common.core.entity.support;


import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.entity.system.Menu;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {
    private String path;
    private String component;
    private String perms;
    private String icon;
    private String type;
    private Integer orderIndex;
}
