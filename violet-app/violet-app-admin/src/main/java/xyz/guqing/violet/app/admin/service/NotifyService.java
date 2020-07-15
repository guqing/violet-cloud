package xyz.guqing.violet.app.admin.service;

/**
 * @author guqing
 * @date 2020-07-14
 */
public interface NotifyService {
    /**
     * 发送一次性邮箱验证码
     * @param email 接收者email
     */
    void sendEmailCaptcha(String email);
}
