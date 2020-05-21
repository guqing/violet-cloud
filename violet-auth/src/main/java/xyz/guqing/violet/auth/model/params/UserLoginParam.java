package xyz.guqing.violet.auth.model.params;

import lombok.Data;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Data
public class UserLoginParam {
    private String username;
    private String password;
    private String grantType;
    private String clientId;
    private String clientSecret;
}
