package xyz.guqing.violet.app.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.app.admin.model.annotation.ControllerEndpoint;
import xyz.guqing.violet.app.admin.model.param.MenuParam;
import xyz.guqing.violet.app.admin.model.param.MenuQuery;
import xyz.guqing.violet.app.admin.service.MenuService;
import xyz.guqing.violet.common.core.model.entity.router.VueRouter;
import xyz.guqing.violet.common.core.model.dto.MenuTree;
import xyz.guqing.violet.common.core.model.entity.system.Menu;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;

import javax.validation.Valid;
import java.util.List;

/**
 * @author guqing
 * @date 2020-05-29
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/tree")
    public ResultEntity<List<VueRouter<Menu>>> getMenu() {
        String username = VioletSecurityHelper.getCurrentUsername();
        List<VueRouter<Menu>> userRouters = menuService.listUserRouters(username);
        return ResultEntity.ok(userRouters);
    }

    @GetMapping("router")
    public ResultEntity<List<Menu>> getRouterList() {
        String username = VioletSecurityHelper.getCurrentUsername();
        List<Menu> menus = menuService.listUserMenus(username);
        return ResultEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResultEntity<Menu> getById(@PathVariable Long id) {
        return ResultEntity.ok(menuService.getById(id));
    }

    @GetMapping
    public ResultEntity<List<MenuTree>> listMenuTree(MenuQuery menuQuery) {
        Menu menu = menuQuery.convertTo();
        List<MenuTree> menuTrees = this.menuService.listTreeMenus(menu);
        return ResultEntity.ok(menuTrees);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('menu:save')")
    @ControllerEndpoint(operation = "保存菜单/按钮", exceptionMessage = "保存菜单/按钮失败")
    public ResultEntity<String> createOrUpdate(@RequestBody @Valid MenuParam menuParam) {
        Menu menu = menuParam.convertTo();
        menuService.saveOrUpdate(menu);
        return ResultEntity.ok();
    }
}
