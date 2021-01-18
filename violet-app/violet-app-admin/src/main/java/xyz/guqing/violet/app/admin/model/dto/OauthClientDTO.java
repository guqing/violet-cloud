package xyz.guqing.violet.app.admin.model.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.common.core.model.constant.StringConstant;
import xyz.guqing.violet.common.core.model.support.OutputConverter;

import java.util.Arrays;
import java.util.List;

/**
 * @author guqing
 * @date 2021-01-14
 */
@Data
public class OauthClientDTO implements OutputConverter<OauthClientDTO, OauthClientDetails> {
    private String clientId;

    private String resourceIds;

    private String scope;

    private List<String> authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private Boolean autoapprove;

    @Override
    @NonNull
    public <T extends OauthClientDTO> T convertFrom(@NonNull OauthClientDetails oauthClientDetails) {
        BeanUtils.copyProperties(oauthClientDetails, this);
        String authorizedGrantTypes = oauthClientDetails.getAuthorizedGrantTypes();
        if(authorizedGrantTypes == null) {
            authorizedGrantTypes = "";
        }
        this.authorizedGrantTypes = Arrays.asList(authorizedGrantTypes.split(StringConstant.COMMA));
        this.autoapprove = Boolean.valueOf(oauthClientDetails.getAutoapprove());
        return (T)this;
    }
}
