package xyz.guqing.common.support.model.entity.system;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * oauth client entity
 * </p>
 *
 * @author guqing
 * @since 2021-01-14
 */
@Data
@Accessors(chain = true)
public class OauthClientDetails {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;
}
