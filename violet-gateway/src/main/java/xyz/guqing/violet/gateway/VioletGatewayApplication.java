package xyz.guqing.violet.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author guqing
 * @date 2020-05-07
 */
@SpringBootApplication
public class VioletGatewayApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(VioletGatewayApplication.class)
                .web(WebApplicationType.REACTIVE)
                .run(args);
    }
}
