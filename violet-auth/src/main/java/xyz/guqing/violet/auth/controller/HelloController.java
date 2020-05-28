package xyz.guqing.violet.auth.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.auth.service.MenuService;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.entity.system.UserConnection;
import xyz.guqing.violet.auth.service.UserConnectionService;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.List;

/**
 * @author guqing
 * @date 2020-05-12
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    @Autowired
    private UserConnectionService userConnectionService;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/test")
    public String test() {
        List<UserConnection> list = userConnectionService.listByUsername("guqing");
        return "hello now you see me"+ JSONObject.toJSONString(list);
    }

    @GetMapping("/hello")
    public String hello() {
        return "say hello,test this api" + JSONObject.toJSONString(userService.loadUserByUsername("guqing"));
    }

    @GetMapping("/menu")
    @ResponseBody
    public ResultEntity<List<VueRouter<Menu>>> getMenu() {
        List<VueRouter<Menu>> userRouters = menuService.getUserRouters("guqing");
        return ResultEntity.ok(userRouters);
    }
}
