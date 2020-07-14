package xyz.guqing.violet.app.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.guqing.violet.app.admin.notify.mail.MailService;
import xyz.guqing.violet.app.admin.service.NotifyService;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 * @date 2020-07-14
 */
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final NotifyService notifyService;

    @GetMapping("captcha")
    public ResultEntity<String> sendCaptcha(@RequestParam String email) {
        notifyService.sendEmailCaptcha(email);
        return ResultEntity.ok();
    }
}
