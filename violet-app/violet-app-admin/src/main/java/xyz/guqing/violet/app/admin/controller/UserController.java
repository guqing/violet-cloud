package xyz.guqing.violet.app.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.List;

/**
 * @author guqing
 * @date 2020-05-30
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResultEntity<PageInfo<UserDTO>> listUserByPage(@RequestBody UserQuery userQuery,
                                                 @RequestBody QueryRequest queryRequest) {
        List<User> users = userService.listByPage(userQuery, queryRequest);
        return ResultEntity.okList(users, user -> new UserDTO().convertFrom(user));
    }
}
