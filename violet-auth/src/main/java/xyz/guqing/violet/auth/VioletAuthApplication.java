package xyz.guqing.violet.auth;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author guqing
 * @date 2020-05-07
 */
@SpringBootApplication
public class VioletAuthApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(VioletAuthApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
