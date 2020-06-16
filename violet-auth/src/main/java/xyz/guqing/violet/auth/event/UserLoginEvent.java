package xyz.guqing.violet.auth.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author guqing
 * @date 2020-06-16
 */
public class UserLoginEvent extends ApplicationEvent {
    private Long userId;
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserLoginEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
