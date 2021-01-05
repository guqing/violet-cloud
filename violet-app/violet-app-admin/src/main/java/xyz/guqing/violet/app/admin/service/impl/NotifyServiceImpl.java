package xyz.guqing.violet.app.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.notify.mail.MailService;
import xyz.guqing.violet.app.admin.service.NotifyService;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.violet.common.redis.service.RedisService;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author guqing
 * @date 2020-07-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {
    private final MailService mailService;
    private final RedisService redisService;

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendEmailCaptcha(String email) {
        Map<String, Object> param = new HashMap<>(2, 1);
        param.put("email", email);
        String captcha = generateCaptcha();
        param.put("captcha", captcha);

        log.debug("一次性邮箱验证码为:{}", captcha);

        // 设置缓存
        redisService.set(VioletConstant.CAPTCHA_PREFIX + email, captcha, VioletConstant.CAPTCHA_EXPIRE);
        // 发送邮件
        mailService.sendTemplateMail(email, "邮箱验证", param, "mail/mail_captcha.ftl");
    }

    private String generateCaptcha() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
