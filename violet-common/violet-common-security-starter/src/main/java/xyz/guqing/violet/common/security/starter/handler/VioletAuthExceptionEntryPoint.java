package xyz.guqing.violet.common.security.starter.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author guqing
 */
@Slf4j
public class VioletAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json;charset=utf-8");

        String requestUri = request.getRequestURI();

        ResultEntity<String> authorizedFailed = ResultEntity.authorizedFailed("访问令牌不合法");

        log.error("客户端访问{}请求失败: {}", requestUri, "访问令牌不合法", authException);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(JSONObject.toJSONString(authorizedFailed));
    }
}
