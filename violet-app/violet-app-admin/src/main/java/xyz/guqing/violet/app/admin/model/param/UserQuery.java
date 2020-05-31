package xyz.guqing.violet.app.admin.model.param;

import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserQuery {
    private String username;
    private Long groupId;
    private Integer gender;
    private Integer status;
    private String mobile;
    private String groupName;
    private LocalDateTime createTimeFrom;
    private LocalDateTime createTimeTo;
    private QueryRequest queryRequest;

    public UserQuery() {
        queryRequest = new QueryRequest();
    }
}
