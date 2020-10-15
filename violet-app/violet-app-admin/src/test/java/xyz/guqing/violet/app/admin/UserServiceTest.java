package xyz.guqing.violet.app.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.params.UserQuery;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.support.PageInfo;

/**
 * @author guqing
 * @date 2020-05-31
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testListByPage() {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername("guqing");
        PageInfo<UserDTO> pageInfo = userService.listByPage(userQuery);
        System.out.println(pageInfo);
    }

    @Test
    void testCountUserBy() {
        UserQuery userQuery = new UserQuery();
        userQuery.setUsername("guqing");
        Long count = userService.countUserBy(userQuery);
        System.out.println(count);
    }

    @Test
    void testEncoder() {
        boolean matches = passwordEncoder.matches("123456", "$2a$10$I/drteyoz3K8ncdS0gw1ve.wDPgfz4p4eZiUWJ4M0948d2Eogc6lG");
        Assert.isTrue(matches, "加密验证不匹配");
    }
}
