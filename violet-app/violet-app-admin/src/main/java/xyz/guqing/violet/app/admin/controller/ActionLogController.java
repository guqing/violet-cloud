package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.model.dto.ActionLogDTO;
import xyz.guqing.violet.app.admin.model.param.ActionLogQuery;
import xyz.guqing.violet.app.admin.service.VioletActionLogService;
import xyz.guqing.violet.common.core.model.entity.system.VioletActionLog;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-07-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/actionlog")
public class ActionLogController {
    private final VioletActionLogService actionLogService;

    @GetMapping
    public ResultEntity<PageInfo<ActionLogDTO>> list(ActionLogQuery actionLogQuery) {
        IPage<VioletActionLog> actionLogPage = actionLogService.listBy(actionLogQuery);
        return ResultEntity.okList(actionLogPage, actionLog -> new ActionLogDTO().convertFrom(actionLog));
    }
}
