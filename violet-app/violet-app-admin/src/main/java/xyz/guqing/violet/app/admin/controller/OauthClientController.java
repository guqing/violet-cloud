package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.common.support.utils.PageUtils;
import xyz.guqing.violet.app.admin.model.dto.OauthClientDTO;
import xyz.guqing.violet.app.admin.model.params.OauthClientParam;
import xyz.guqing.violet.app.admin.service.OauthClientService;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import javax.validation.Valid;

/**
 * @author guqing
 * @date 2021-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/clients")
public class OauthClientController {
    private final OauthClientService oauthClientService;

    @GetMapping
    public ResultEntity<PageInfo<OauthClientDTO>> listBy(String clientId, PageQuery pageQuery) {
        Page<OauthClientDetails> clientDetailsPage = oauthClientService.listBy(clientId, PageUtils.convertFrom(pageQuery));
        PageInfo<OauthClientDTO> pageInfo = PageUtils.convertTo(clientDetailsPage, client -> new OauthClientDTO().convertFrom(client));
        return ResultEntity.ok(pageInfo);
    }

    @GetMapping("/{clientId}")
    public ResultEntity<OauthClientDTO> getByClientId(@PathVariable String clientId) {
        OauthClientDetails oauthClientDetails = oauthClientService.getById(clientId);
        return ResultEntity.ok(new OauthClientDTO().convertFrom(oauthClientDetails));
    }

    @GetMapping("/check/{clientId}")
    public ResultEntity<Boolean> checkExistByClientId(@PathVariable String clientId) {
        boolean exist = oauthClientService.existByClientId(clientId);
        return ResultEntity.ok(exist);
    }

    @PostMapping
    public ResultEntity<String> create(@RequestBody @Valid OauthClientParam oauthClientParam) {
        OauthClientDetails oauthClientDetails = oauthClientParam.convertTo();
        oauthClientService.createBy(oauthClientDetails);
        return ResultEntity.ok();
    }
}
