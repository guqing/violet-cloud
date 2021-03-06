package xyz.guqing.common.support.model.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.common.support.model.entity.BaseEntity;

/**
 * <p>
 * 用户分组表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserGroup extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级分组id
     */
    private Long parentId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 排序
     */
    private Long sortIndex;
}
