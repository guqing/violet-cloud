package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.PageQuery;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Data
public class RoleQuery {
    private Long id;
    private String roleName;
    private String remark;
    private String createTime;
    private PageQuery pageQuery = new PageQuery();
}
