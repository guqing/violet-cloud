package xyz.guqing.violet.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author guqing
 * @date 2020-05-12
 */
@RestController
@RequestMapping("/test")
public class HelloController {
    @GetMapping("/test")
    public String test() {
        return "hello now you see me";
    }

    @GetMapping("/hello")
    public String hello() {
        return "say hello,test this api";
    }
}
