package xyz.guqing.violet.app.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.service.OauthClientService;

/**
 * @author guqing
 * @date 2021-01-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth/clients")
public class OauthClientController {
    private final OauthClientService oauthClientService;
}
