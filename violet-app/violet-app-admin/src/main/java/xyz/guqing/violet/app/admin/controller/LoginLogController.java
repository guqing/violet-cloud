package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.model.dto.UserLoginLogDTO;
import xyz.guqing.violet.app.admin.model.params.LoginLogParam;
import xyz.guqing.violet.app.admin.service.UserLoginLogService;
import xyz.guqing.common.support.utils.PageConvert;
import xyz.guqing.common.support.model.entity.system.UserLoginLog;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Slf4j
@RestController
@RequestMapping("/log/login")
@RequiredArgsConstructor
public class LoginLogController {
    private final UserLoginLogService userLoginLogService;

    @GetMapping
    public ResultEntity<PageInfo<UserLoginLogDTO>> list(LoginLogParam loginLogParam, PageQuery queryRequest) {
        log.debug("登录日志列表参数:[{}]", queryRequest);
        IPage<UserLoginLog> userLoginLogs = userLoginLogService.listBy(loginLogParam,queryRequest);
        return ResultEntity.ok(convertTo(userLoginLogs));
    }

    private PageInfo<UserLoginLogDTO> convertTo(IPage<UserLoginLog> userLoginLogs) {
        return PageConvert.convertFrom(userLoginLogs, userLoginLog -> new UserLoginLogDTO().convertFrom(userLoginLog));
    }
}
