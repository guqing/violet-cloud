package xyz.guqing.violet.app.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.model.dto.RoleDTO;
import xyz.guqing.violet.app.admin.model.param.RoleQuery;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.common.core.model.support.PageInfo;

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
        QueryRequest queryRequest = new QueryRequest();
        RoleQuery role = new RoleQuery();
        role.setQueryRequest(queryRequest);
        Page<Role> pageInfo = roleService.listBy(role);
        System.out.println(JSONObject.toJSONString(pageInfo));
    }
}
