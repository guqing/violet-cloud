package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.InputConverter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author guqing
 * @date 2020-06-06
 */
@Data
public class MenuParam implements InputConverter<Menu> {
    private Long id;

    private Long parentId;

    @NotBlank(message = "菜单标题不能为空")
    @Size(max = 150, message = "菜单标题不能字符长度不能大于 {max}")
    private String title;

    @NotBlank(message = "资源类型不能为空")
    @Size(max = 1, message = "资源类型只能是0或1")
    private String type;

    private String path;

    private String redirect;

    private String name;

    private String component;

    private String perms;

    private String icon;

    private Boolean hidden;

    private Boolean keepAlive;

    private Long sortIndex;

    @Override
    public Menu convertTo() {
        if(parentId == null) {
            parentId = 0L;
        }
        if(sortIndex == null) {
            sortIndex = 0L;
        }
        return InputConverter.super.convertTo();
    }
}
