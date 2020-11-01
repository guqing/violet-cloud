package xyz.guqing.violet.apm.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author guqing
 * @date 2020-11-01
 */
@EnableAdminServer
@SpringBootApplication
public class VioletAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(VioletAdminApplication.class, args);
    }
}
