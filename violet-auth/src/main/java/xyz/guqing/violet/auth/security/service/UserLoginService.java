package xyz.guqing.violet.auth.security.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.entity.constant.GrantTypeConstant;
import xyz.guqing.violet.common.core.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.NotFoundException;
import xyz.guqing.violet.common.core.model.params.UserLoginParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-05-14
 */
@Service
public class UserLoginService {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final ResourceOwnerPasswordTokenGranter granter;
    private final JdbcClientDetailsService jdbcClientDetailsService;

    public UserLoginService(ResourceOwnerPasswordTokenGranter granter,
                            JdbcClientDetailsService jdbcClientDetailsService) {
        this.granter = granter;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

    /**
     * 根据用户信息获取token
     * @param user 用户信息
     * @return 认证成功返回OAuth2AccessToken
     * @throws BadRequestException 查询第三方可用ClientDetails出错
     * @throws NotFoundException 获取不到第三方可以用的ClientDetails
     */
    public OAuth2AccessToken getOauth2AccessToken(UserLoginParam user) {
        ClientDetails clientDetails = null;
        try {
            clientDetails = jdbcClientDetailsService.loadClientByClientId(user.getClientId());
        } catch (Exception e) {
            throw new BadRequestException("获取第三方登录可用的Client失败");
        }

        if(clientDetails == null) {
            throw new NotFoundException("未找到第三方登录可用的Client");
        }

        Map<String, String> requestParameters = new HashMap<>(5, 1);
        requestParameters.put(ParamsConstant.GRANT_TYPE, user.getGrantType());
        requestParameters.put(USERNAME, user.getUsername());
        requestParameters.put(PASSWORD, user.getPassword());

        String grantTypes = String.join(",", clientDetails.getAuthorizedGrantTypes());
        TokenRequest tokenRequest = new TokenRequest(requestParameters, clientDetails.getClientId(), clientDetails.getScope(), grantTypes);
        return granter.grant(GrantTypeConstant.PASSWORD, tokenRequest);
    }


}
