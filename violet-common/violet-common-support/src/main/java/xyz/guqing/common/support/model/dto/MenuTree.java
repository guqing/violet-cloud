package xyz.guqing.common.support.model.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.common.support.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.Tree;

/**
 * @author guqing
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends Tree<Menu> {
    private String icon;
    private String type;
}
