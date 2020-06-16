package xyz.guqing.violet.auth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.auth.event.UserLoginEvent;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.constant.VioletConstant;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-06-16
 */
@Component
public class UserLoginListener implements ApplicationListener<UserLoginEvent> {
    private final UserService userService;

    public UserLoginListener(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void onApplicationEvent(@NonNull UserLoginEvent userLoginEvent) {
        userService.updateLastLoginTime(userLoginEvent.getUserId(), LocalDateTime.now());
    }
}
