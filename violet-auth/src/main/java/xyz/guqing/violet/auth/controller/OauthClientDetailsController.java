package xyz.guqing.violet.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.violet.auth.entity.OauthClientDetails;
import xyz.guqing.violet.auth.service.OauthClientDetailsService;
import xyz.guqing.violet.common.core.entity.QueryRequest;
import xyz.guqing.violet.common.core.model.support.ResultEntity;
import xyz.guqing.violet.common.core.utils.FebsUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author guqing
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("client")
public class OauthClientDetailsController {

    private final OauthClientDetailsService oauthClientDetailsService;

    @GetMapping("check/{clientId}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        return client == null;
    }

    @GetMapping("secret/{clientId}")
    @PreAuthorize("hasAuthority('client:decrypt')")
    public ResultEntity<String> getOriginClientSecret(@NotBlank(message = "{required}") @PathVariable String clientId) {
        OauthClientDetails client = this.oauthClientDetailsService.findById(clientId);
        String origin = client != null ? client.getOriginSecret() : StringUtils.EMPTY;
        return ResultEntity.ok(origin);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('client:view')")
    public ResultEntity<Map<String, Object>> oauthCliendetailsList(QueryRequest request, OauthClientDetails oAuthClientDetails) {
        Map<String, Object> dataTable = FebsUtil.getDataTable(this.oauthClientDetailsService.findOauthClientDetails(request, oAuthClientDetails));
        return ResultEntity.ok(dataTable);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('client:add')")
    public ResultEntity<String> addOauthCliendetails(@Valid OauthClientDetails oAuthClientDetails) {
        this.oauthClientDetailsService.createOauthClientDetails(oAuthClientDetails);
        return ResultEntity.ok();
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('client:delete')")
    public ResultEntity<String> deleteOauthCliendetails(@NotBlank(message = "{required}") String clientIds) {
            this.oauthClientDetailsService.deleteOauthClientDetails(clientIds);
            return ResultEntity.ok();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('client:update')")
    public ResultEntity<String> updateOauthCliendetails(@Valid OauthClientDetails oAuthClientDetails) {
        this.oauthClientDetailsService.updateOauthClientDetails(oAuthClientDetails);
        return ResultEntity.ok();
    }
}
