package xyz.guqing.violet.common.security.starter.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author guqing
 */
public class VioletAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json;charset=utf-8");

        ResultEntity<String> accessDenied = ResultEntity.accessDenied("没有权限访问该资源");
        response.getWriter().write(JSONObject.toJSONString(accessDenied));
    }
}
