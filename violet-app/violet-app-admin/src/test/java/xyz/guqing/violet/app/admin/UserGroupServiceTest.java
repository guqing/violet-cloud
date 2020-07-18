package xyz.guqing.violet.app.admin;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.common.support.model.dto.UserGroupTree;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-06
 */
@SpringBootTest
class UserGroupServiceTest {
    @Autowired
    private UserGroupService userGroupService;
    @Test
    void test() {
        List<UserGroupTree> userGroupTrees = userGroupService.listBy(null);
        System.out.println(JSONObject.toJSONString(userGroupTrees));
    }
}
