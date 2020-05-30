package xyz.guqing.violet.app.admin.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.OutputConverter;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Data
public class UserDTO implements OutputConverter<UserDTO, User>{
    private String username;

    private String email;

    private String mobile;

    private String groupName;

    private String avatar;

    private String description;

    private Integer status;

    private LocalDateTime createTime;
}
