package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Data
public class LoginLogParam {
    private String username;
    private LocalDateTime createFrom;
    private LocalDateTime createTo;
}
