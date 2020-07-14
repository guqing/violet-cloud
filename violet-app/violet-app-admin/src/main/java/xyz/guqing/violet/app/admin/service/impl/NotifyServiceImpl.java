package xyz.guqing.violet.app.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.notify.mail.MailService;
import xyz.guqing.violet.app.admin.service.NotifyService;
import xyz.guqing.violet.common.core.utils.RedisUtils;
import xyz.guqing.violet.common.redis.service.RedisService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-07-14
 */
@Service
@RequiredArgsConstructor
public class NotifyServiceImpl implements NotifyService {
    /**
     * 验证码缓存有效期5分钟
     */
    private static final Long CAPTCHA_EXPIRE = 300L;

    private final MailService mailService;
    private final RedisService redisService;

    @Override
    public void sendEmailCaptcha(String email) {
        Map<String,Object> param = new HashMap<>(2,1);
        param.put("email", email);
        int captcha = (int)(Math.random()*9+1)*100000;
        param.put("captcha", captcha);

        // 设置缓存
        redisService.set(captchaCacheKey(email), captcha, CAPTCHA_EXPIRE);
        // 发送邮件
        mailService.sendTemplateMail(email, "邮箱验证",param, "mail/mail_captcha.ftl");
    }

    @Override
    public boolean checkEmailCaptcha(String email, String captcha) {
        Object value = redisService.get(captchaCacheKey(email));
        return value != null && value.equals(captcha);
    }

    private String captchaCacheKey(String email) {
        return RedisUtils.keyBuilder("app_admin", "notify", "captcha", email);
    }
}
