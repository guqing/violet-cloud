package xyz.guqing.violet.auth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.auth.event.UserLoginEvent;
import xyz.guqing.violet.auth.service.UserLoginLogService;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.common.support.model.entity.system.UserLoginLog;
import xyz.guqing.violet.common.core.utils.RegionAddressUtils;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-06-16
 */
@Component
public class UserLoginListener implements ApplicationListener<UserLoginEvent> {
    private final UserService userService;
    private final UserLoginLogService userLoginLogService;

    public UserLoginListener(UserService userService,
                             UserLoginLogService userLoginLogService) {
        this.userService = userService;
        this.userLoginLogService = userLoginLogService;
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void onApplicationEvent(@NonNull UserLoginEvent userLoginEvent) {
        // 更新最后登录时间
        userService.updateLastLoginTime(userLoginEvent.getUsername(), LocalDateTime.now());

        // 保存登录日志
        userLoginLogService.save(buildLoginLog(userLoginEvent.getUsername()));
    }

    private UserLoginLog buildLoginLog(String username) {
        UserLoginLog loginLog = new UserLoginLog();
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setUsername(username);

        String ip = VioletUtil.getRequestIpAddress();
        loginLog.setIp(ip);
        loginLog.setLocation(RegionAddressUtils.getCityInfo(ip));

        HttpServletRequest servletRequest = VioletUtil.getHttpServletRequest();
        String userAgent = servletRequest.getHeader("user-agent");
        Map<String, String> systemBrowser = VioletUtil.getSystemBrowserInfoByUserAgent(userAgent);

        loginLog.setSystem(systemBrowser.get("system"));
        loginLog.setBrowser(systemBrowser.get("browser"));

        return loginLog;
    }
}
