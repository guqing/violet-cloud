package xyz.guqing.violet.auth;

import com.alibaba.fastjson.JSONObject;
import com.xkcoding.justauth.AuthRequestFactory;
import com.xkcoding.justauth.properties.JustAuthProperties;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.config.AuthSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-07-27
 */
@SpringBootTest
class JustAuthTest {
    @Autowired
    private AuthRequestFactory authRequestFactory;
    @Autowired
    private JustAuthProperties justAuthProperties;

    @Test
    void test() {
        Map<AuthSource, AuthConfig> type = justAuthProperties.getType();
        System.out.println(JSONObject.toJSONString(type));
    }
}
