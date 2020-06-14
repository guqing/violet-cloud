package xyz.guqing.violet.app.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.app.admin.model.annotation.ControllerEndpoint;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.param.UserParam;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-30
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('user:view')")
    public ResultEntity<PageInfo<UserDTO>> listUserByPage(UserQuery userQuery) {
        log.debug("list user 查询条件：{}", userQuery);
        PageInfo<UserDTO> users = userService.listByPage(userQuery);
        return ResultEntity.ok(users);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:add')")
    @ControllerEndpoint(operation = "新增用户", exceptionMessage = "新增用户失败")
    public ResultEntity<String> addUser(@Valid UserParam userParam) {
        userService.createUser(userParam);
        return ResultEntity.ok();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:update')")
    @ControllerEndpoint(operation = "修改用户", exceptionMessage = "修改用户失败")
    public ResultEntity<String> updateUser(@Valid UserParam userParam) {
        userService.updateUser(userParam);
        return ResultEntity.ok();
    }

    @PutMapping("profile")
    @ControllerEndpoint(exceptionMessage = "修改个人信息失败")
    public void updateProfile(@Valid UserParam userParam) {
    }

    @PutMapping("avatar")
    @ControllerEndpoint(exceptionMessage = "修改头像失败")
    public void updateAvatar(@RequestParam String avatar) {
    }


    @DeleteMapping
    @PreAuthorize("hasAuthority('user:delete')")
    @ControllerEndpoint(operation = "删除用户", exceptionMessage = "删除用户失败")
    public ResultEntity<String> deleteUsers(@RequestBody List<Long> userIds) {
        // 使用逻辑删除
        userService.removeByIds(userIds);
        return ResultEntity.ok();
    }
}
