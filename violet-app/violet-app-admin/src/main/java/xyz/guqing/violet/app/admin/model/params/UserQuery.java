package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.QueryRequest;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserQuery {
    private String username;
    private String nickname;
    private Long groupId;
    private Integer gender;
    private Integer status;
    private String mobile;
    private String groupName;
    private LocalDateTime createTimeFrom;
    private LocalDateTime createTimeTo;
    private QueryRequest queryRequest = new QueryRequest();
}
