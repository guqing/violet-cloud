package xyz.guqing.violet.auth.model.dto;

import lombok.Data;

/**
 * @author guqing
 * @date 2020-07-28
 */
@Data
public class SocialRelationDTO {
    private String provider;
    private Boolean isConnected;
}
