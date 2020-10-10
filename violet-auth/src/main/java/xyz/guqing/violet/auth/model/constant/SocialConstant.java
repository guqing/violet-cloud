package xyz.guqing.violet.auth.model.constant;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author guqing
 */
public interface SocialConstant {
    ThreadLocal<String> PASSWORD_THREAD_LOCAL = new ThreadLocal<>();

    String SOCIAL_LOGIN = "social_login";

    String USERNAME = "username";
    String PASSWORD = "password";

    /**
     * 获取随机生成的密码
     *
     * @return String 密码
     */
    static String getSocialLoginPassword() {
        String password = PASSWORD_THREAD_LOCAL.get();
        PASSWORD_THREAD_LOCAL.remove();
        return password;
    }

    /**
     * 设置随机生成的密码
     *
     * @return String 密码
     */
    static String setSocialLoginPassword() {
        String randomPassword = RandomStringUtils.randomAlphanumeric(64);
        PASSWORD_THREAD_LOCAL.set(randomPassword);
        return randomPassword;
    }
}
