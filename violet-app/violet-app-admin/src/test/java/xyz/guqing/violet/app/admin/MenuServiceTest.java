package xyz.guqing.violet.app.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.model.enums.MenuType;
import xyz.guqing.violet.app.admin.model.params.MenuQuery;
import xyz.guqing.violet.app.admin.service.MenuService;
import xyz.guqing.common.support.model.dto.MenuTree;
import xyz.guqing.common.support.model.entity.system.Menu;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-04
 */
@SpringBootTest
class MenuServiceTest {
    @Autowired
    private MenuService menuService;

    @Test
    void test() {
        MenuQuery menuQuery = new MenuQuery();
        menuQuery.setType(MenuType.MENU.getValue());
        Menu menu = menuQuery.convertTo();
        List<MenuTree> menuTrees = menuService.listTreeMenus(menu);
    }
}
