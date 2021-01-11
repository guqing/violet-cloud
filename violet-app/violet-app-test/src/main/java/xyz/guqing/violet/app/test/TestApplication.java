package xyz.guqing.violet.app.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import xyz.guqing.violet.common.security.starter.annotation.EnableVioletCloudResourceServer;

/**
 * @author guqing
 * @date 2020-05-08
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableVioletCloudResourceServer
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
