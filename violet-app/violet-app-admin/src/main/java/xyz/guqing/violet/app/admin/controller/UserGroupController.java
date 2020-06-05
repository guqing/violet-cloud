package xyz.guqing.violet.app.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.support.UserGroupTree;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-06-05
 */
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService userGroupService;

    @GetMapping("/list")
    public ResultEntity<PageInfo<UserGroupTree>> list(String name, QueryRequest queryRequest) {
        PageInfo<UserGroupTree> pageInfo = userGroupService.listByPage(name, queryRequest);
        return ResultEntity.ok(pageInfo);
    }
}
