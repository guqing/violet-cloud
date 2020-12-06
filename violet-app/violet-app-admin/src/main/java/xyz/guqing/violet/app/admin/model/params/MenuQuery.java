package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;
import xyz.guqing.common.support.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.InputConverter;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Data
public class MenuQuery {
    private Long orderIndex;
    private String type;
    private String title;
}
