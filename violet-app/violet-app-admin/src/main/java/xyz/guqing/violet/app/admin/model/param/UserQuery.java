package xyz.guqing.violet.app.admin.model.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
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

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTimeFrom;

    @JsonFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTimeTo;
    private QueryRequest queryRequest;

    public UserQuery() {
        queryRequest = new QueryRequest();
    }
}
