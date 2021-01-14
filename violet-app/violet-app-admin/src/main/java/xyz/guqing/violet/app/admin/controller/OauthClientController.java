package xyz.guqing.violet.app.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.common.support.utils.PageUtils;
import xyz.guqing.violet.app.admin.model.dto.OauthClientDTO;
import xyz.guqing.violet.app.admin.service.OauthClientService;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

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
        Page<OauthClientDetails> clientDetailsPage = oauthClientService.listBy(clientId, PageUtils.convert(pageQuery));
        PageInfo<OauthClientDTO> pageInfo = PageUtils.convertToPageInfo(clientDetailsPage, client -> new OauthClientDTO().convertFrom(client));
        return ResultEntity.ok(pageInfo);
    }
}
