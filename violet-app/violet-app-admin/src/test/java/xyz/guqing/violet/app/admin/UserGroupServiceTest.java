package xyz.guqing.violet.app.admin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.support.UserGroupTree;
import xyz.guqing.violet.common.core.model.support.PageInfo;

/**
 * @author guqing
 * @date 2020-06-06
 */
@SpringBootTest
public class UserGroupServiceTest {
    @Autowired
    private UserGroupService userGroupService;
    @Test
    void test() {
        QueryRequest queryRequest = new QueryRequest();
        PageInfo<UserGroupTree> pageInfo = userGroupService.listByPage(null, queryRequest);
        System.out.println(pageInfo);
    }
}
