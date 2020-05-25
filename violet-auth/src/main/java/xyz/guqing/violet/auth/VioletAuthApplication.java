package xyz.guqing.violet.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author guqing
 * @date 2020-05-07
 */
@SpringBootApplication
@MapperScan("xyz.guqing.violet.auth.mapper")
public class VioletAuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(VioletAuthApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
