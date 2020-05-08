package xyz.guqing.violet.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.violet.auth.model.base.BaseEntity;

/**
 * <p>
 * 客户端配置表
 * </p>
 *
 * @author guqing
 * @since 2020-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("rms_oauth_client_details")
public class OauthClientDetails extends BaseEntity {

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

    private Integer autoapprove;

    private String originSecret;
}
