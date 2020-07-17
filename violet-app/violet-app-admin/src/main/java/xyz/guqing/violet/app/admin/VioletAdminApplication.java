package xyz.guqing.violet.app.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.guqing.violet.common.security.starter.annotation.EnableVioletCloudResourceServer;

/**
 * @author guqing
 * @date 2020-05-29
 */
@EnableAsync
@SpringBootApplication
@EnableVioletCloudResourceServer
@MapperScan("xyz.guqing.violet.app.admin.mapper")
public class VioletAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(VioletAdminApplication.class, args);
    }
}
