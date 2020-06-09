package xyz.guqing.violet.app.admin.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.app.admin.model.annotation.ControllerEndpoint;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.model.param.RoleParam;
import xyz.guqing.violet.app.admin.model.param.RoleQuery;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.validation.Valid;

/**
 * @author guqing
 * @date 2020-06-04
 */
@Slf4j
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("list")
    public ResultEntity<PageInfo<RoleDTO>> listBy(RoleQuery roleQuery, QueryRequest queryRequest) {
        log.debug("角色查询对象：{}", JSONObject.toJSONString(roleQuery));
        roleQuery.setQueryRequest(queryRequest);
        PageInfo<RoleDTO> pageInfo = roleService.listBy(roleQuery);
        return ResultEntity.ok(pageInfo);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('role:save')")
    @ControllerEndpoint(operation = "保存/更新", exceptionMessage = "保存/更新角色失败")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid RoleParam roleParam) {
        Role role = roleParam.convertTo();
        roleService.createOrUpdate(role, roleParam.getMenuIds());
        return ResultEntity.ok();
    }
}
