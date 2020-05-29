package xyz.guqing.violet.auth.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.system.UserConnection;
import xyz.guqing.violet.auth.service.UserConnectionService;

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

    @GetMapping("/test")
    public String test() {
        List<UserConnection> list = userConnectionService.listByUsername("guqing");
        return "hello now you see me"+ JSONObject.toJSONString(list);
    }

    @GetMapping("/hello")
    public String hello() {
        return "say hello,test this api" + JSONObject.toJSONString(userService.loadUserByUsername("guqing"));
    }
}
