package xyz.guqing.violet.app.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.service.RoleService;

/**
 * @author guqing
 * @date 2020-06-05
 */
@SpringBootTest
class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @Test
    void test() {
        RoleDTO roleById = roleService.getRoleById(1L);
        System.out.println(roleById);
    }
}
