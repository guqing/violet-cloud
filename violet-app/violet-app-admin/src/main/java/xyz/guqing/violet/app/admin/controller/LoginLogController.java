package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.model.dto.UserLoginLogDTO;
import xyz.guqing.violet.app.admin.model.params.LoginLogParam;
import xyz.guqing.violet.app.admin.service.UserLoginLogService;
import xyz.guqing.violet.common.core.model.entity.system.UserLoginLog;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-07-17
 */
@RestController
@RequestMapping("/log/login")
@RequiredArgsConstructor
public class LoginLogController {
    private final UserLoginLogService userLoginLogService;

    @GetMapping
    public ResultEntity<PageInfo<UserLoginLogDTO>> list(LoginLogParam loginLogParam, QueryRequest queryRequest) {
        IPage<UserLoginLog> userLoginLogs = userLoginLogService.listBy(loginLogParam,queryRequest);
        return ResultEntity.okList(userLoginLogs, userLoginLog -> new UserLoginLogDTO().convertFrom(userLoginLog));
    }
}
