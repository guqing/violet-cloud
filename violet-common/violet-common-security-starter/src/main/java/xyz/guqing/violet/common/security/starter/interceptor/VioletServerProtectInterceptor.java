package xyz.guqing.violet.common.security.starter.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.guqing.violet.common.core.entity.constant.VioletConstant;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.security.starter.properties.VioletCloudSecurityProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author guqing
 */
public class VioletServerProtectInterceptor implements HandlerInterceptor {
    private VioletCloudSecurityProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json;charset=utf-8");

        if (!properties.getOnlyFetchByGateway()) {
            return true;
        }

        // 从请求头中获取 Gateway Token
        String token = request.getHeader(VioletConstant.GATEWAY_TOKEN_HEADER);
        String gatewayToken = new String(Base64Utils.encode(VioletConstant.GATEWAY_TOKEN_VALUE.getBytes()));
        // 校验 Gateway Token的正确性
        if (StringUtils.equals(gatewayToken, token)) {
            return true;
        } else {
            ResultEntity<String> accessDenied = ResultEntity.accessDenied("请通过网关获取资源");
            response.getWriter().write(JSONObject.toJSONString(accessDenied));
            return false;
        }
    }

    public void setProperties(VioletCloudSecurityProperties properties) {
        this.properties = properties;
    }
}
