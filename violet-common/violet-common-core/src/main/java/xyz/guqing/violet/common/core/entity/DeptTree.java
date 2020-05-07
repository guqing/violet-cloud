package xyz.guqing.violet.common.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.guqing.violet.common.core.entity.system.Dept;

/**
 * @author MrBird
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptTree extends Tree<Dept> {

    private Integer orderNum;
}
