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

    /**
     * 校验一次性邮箱验证码正确性
     * @param email 邮箱地址
     * @param captcha 验证码
     * @return 如果校验正确返回{@code true},否则返回{@code false}
     */
    boolean checkEmailCaptcha(String email, String captcha);
}
