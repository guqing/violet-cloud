package xyz.guqing.common.support.model.entity.system;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.common.support.model.entity.BaseEntity;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 上级菜单ID
     */
    private Long parentId;

    /**
     * 菜单路由名称，英文
     */
    private String name;

    /**
     * 菜单/按钮的标题
     */
    private String title;

    /**
     * 是否显示sidebar
     */
    private Boolean hidden;

    private Boolean keepAlive;

    /**
     * 对应路由path
     */
    private String path;

    /**
     * 菜单的重定向路径
     */
    private String redirect;

    /**
     * 对应路由组件component
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 图标
     */
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    private String type;

    /**
     * 排序
     */
    private Long sortIndex;
}
