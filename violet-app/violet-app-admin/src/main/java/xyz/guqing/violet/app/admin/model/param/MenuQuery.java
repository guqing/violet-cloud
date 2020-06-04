package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.InputConverter;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
public class MenuQuery implements InputConverter<Menu> {
    private Long orderIndex;
    private String type;
}
