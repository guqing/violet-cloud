package xyz.guqing.violet.app.admin.model.params;

import lombok.Data;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.common.core.model.support.CreateCheck;
import xyz.guqing.violet.common.core.model.support.InputConverter;
import xyz.guqing.violet.common.core.model.support.UpdateCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author guqing
 * @date 2021-01-16
 */
@Data
public class OauthClientParam implements InputConverter<OauthClientDetails> {
    @NotBlank(message = "客户端ID不能为空", groups = CreateCheck.class)
    private String clientId;

    @NotBlank(message = "客户端密钥不能为空", groups = CreateCheck.class)
    private String clientSecret;

    @NotBlank(message = "Scope权限范围不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private String scope;

    @NotNull(message = "授权方式不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private List<String> authorizedGrantTypes;

    @NotNull(message = "access token过期时间不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private Integer accessTokenValidity;

    @NotNull(message = "refresh token过期时间不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private Integer refreshTokenValidity;

    @NotNull(message = "自动授权不能为空", groups = {CreateCheck.class, UpdateCheck.class})
    private Boolean autoapprove;

    private String resourceIds;

    private String webServerRedirectUri;

    private String authorities;

    @Override
    public OauthClientDetails convertTo() {
        OauthClientDetails oauthClientDetails = InputConverter.super.convertTo();
        convertType(oauthClientDetails);
        return oauthClientDetails;
    }

    @Override
    public void update(OauthClientDetails oauthClientDetails) {
        // 将密钥置空防止被修改
        this.clientId = null;
        InputConverter.super.update(oauthClientDetails);
        convertType(oauthClientDetails);
    }

    private void convertType(OauthClientDetails oauthClientDetails) {
        oauthClientDetails.setAutoapprove(String.valueOf(autoapprove));
        oauthClientDetails.setAuthorizedGrantTypes(String.join(",", authorizedGrantTypes));
    }
}
