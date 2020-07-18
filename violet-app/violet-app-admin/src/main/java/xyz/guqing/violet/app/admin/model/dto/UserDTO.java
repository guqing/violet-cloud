package xyz.guqing.violet.app.admin.model.dto;

import lombok.Data;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.violet.common.core.model.support.OutputConverter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserDTO implements OutputConverter<UserDTO, UserDO>{
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String mobile;

    private Long groupId;

    private String groupName;

    private List<String> roleIds;

    private List<String> roleNames;

    private String avatar;

    private String description;

    private Integer status;

    private LocalDateTime createTime;
}
