package xyz.guqing.violet.gateway.enhance.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteUser;

/**
 * @author guqing
 * @date 2020-07-20
 */
@Data
public class UserQuery implements InputConverter<RouteUser> {
    private String username;
    private String roles;
}
