package xyz.guqing.violet.app.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.app.admin.model.param.UserGroupParam;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.violet.common.core.model.dto.UserGroupTree;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.validation.Valid;
import java.util.List;

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
    public ResultEntity<List<UserGroupTree>> list(String name) {
        List<UserGroupTree> userGroupTrees = userGroupService.listBy(name);
        return ResultEntity.ok(userGroupTrees);
    }

    @GetMapping("/{id}")
    public ResultEntity<UserGroup> getById(@PathVariable Long id) {
        return  ResultEntity.ok(userGroupService.getById(id));
    }

    @PostMapping("/save")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid UserGroupParam userGroupParam) {
        UserGroup userGroup = userGroupParam.convertTo();
        userGroupService.createOrUpdate(userGroup);
        return ResultEntity.ok();
    }
}
