package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.common.support.utils.PageUtils;
import xyz.guqing.violet.app.admin.model.dto.ActionLogDTO;
import xyz.guqing.violet.app.admin.model.params.ActionLogQuery;
import xyz.guqing.violet.app.admin.service.VioletActionLogService;
import xyz.guqing.common.support.model.entity.system.VioletActionLog;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-07-11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/log/action")
public class ActionLogController {
    private final VioletActionLogService actionLogService;

    @GetMapping
    public ResultEntity<PageInfo<ActionLogDTO>> list(ActionLogQuery actionLogQuery, PageQuery pageQuery) {
        IPage<VioletActionLog> actionLogPage = actionLogService.listBy(actionLogQuery,pageQuery);
        return ResultEntity.ok(convertTo(actionLogPage));
    }

    private PageInfo<ActionLogDTO> convertTo(IPage<VioletActionLog> actionLogPage) {
        return PageUtils.convertTo(actionLogPage, actionLog -> new ActionLogDTO().convertFrom(actionLog));
    }
}
