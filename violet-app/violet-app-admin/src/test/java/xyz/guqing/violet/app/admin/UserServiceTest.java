package xyz.guqing.violet.app.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.support.PageInfo;

import java.util.Random;
import java.util.UUID;

/**
 * @author guqing
 * @date 2020-05-31
 */
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

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
}
