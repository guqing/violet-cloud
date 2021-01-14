package xyz.guqing.violet.app.admin.model.dto;

import lombok.Data;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.common.core.model.support.OutputConverter;

/**
 * @author guqing
 * @date 2021-01-14
 */
@Data
public class OauthClientDTO implements OutputConverter<OauthClientDTO, OauthClientDetails> {
    private String clientId;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}
