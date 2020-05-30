package xyz.guqing.violet.app.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.service.MenuService;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.List;

/**
 * @author guqing
 * @date 2020-05-29
 */
@RestController
@RequestMapping("/menu")
public class TestController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/tree")
    public ResultEntity<List<VueRouter<Menu>>> getMenu() {
        List<VueRouter<Menu>> userRouters = menuService.getUserRouters("guqing");
        return ResultEntity.ok(userRouters);
    }

    @GetMapping("router")
    public ResultEntity<List<Menu>> getRouterList() {
        List<Menu> menus = menuService.findUserMenus("guqing");
        return ResultEntity.ok(menus);
    }
}
