package xyz.guqing.violet.app.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guqing
 * @date 2020-05-08
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello now you see me";
    }
}
