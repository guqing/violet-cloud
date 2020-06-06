package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.QueryRequest;

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
    private QueryRequest queryRequest;
}
