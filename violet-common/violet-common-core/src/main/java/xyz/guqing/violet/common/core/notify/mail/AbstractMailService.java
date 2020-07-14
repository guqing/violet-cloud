package xyz.guqing.violet.common.core.notify.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;
import xyz.guqing.violet.common.core.exception.EmailException;
import xyz.guqing.violet.common.core.notify.properties.EmailProperties;
import xyz.guqing.violet.common.core.service.SettingOptionService;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author guqing
 * @date 2020-07-14
 */
@Slf4j
abstract class AbstractMailService implements MailService{
    private final SettingOptionService settingOptionService;

    AbstractMailService(SettingOptionService settingOptionService) {
        this.settingOptionService = settingOptionService;
    }

    void sendMailTemplate(@Nullable Callback callback) {
        if (callback == null) {
            log.info("Callback is null, skip to send email");
            return;
        }

        // check if mail is enable
        Boolean emailEnabled = settingOptionService.getByPropertyOrDefault(EmailProperties.ENABLED, Boolean.class);

        if (!emailEnabled) {
            // If disabled
            log.info("Email has been disabled by yourself, you can re-enable it through email settings on admin page.");
            return;
        }

        // get mail sender
        JavaMailSender mailSender = getMailSender();

        // create mime message helper
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage());

        try {
            // set from-name
            messageHelper.setFrom(getMailFrom(mailSender));
            // handle message set separately
            callback.handle(messageHelper);

            // get mime message
            MimeMessage mimeMessage = messageHelper.getMimeMessage();
            // send email
            mailSender.send(mimeMessage);

            log.info("Sent an email to [{}] successfully, subject: [{}], sent date: [{}]",
                    Arrays.toString(mimeMessage.getAllRecipients()),
                    mimeMessage.getSubject(),
                    mimeMessage.getSentDate());
        } catch (Exception e) {
            throw new EmailException("邮件发送失败，请检查 SMTP 服务配置是否正确", e);
        }
    }

    private synchronized MailProperties getMailProperties() {
        MailProperties mailProperties = new MailProperties();

        // set properties
        mailProperties.setHost(settingOptionService.getByPropertyOrDefault(EmailProperties.HOST, String.class));
        mailProperties.setPort(settingOptionService.getByPropertyOrDefault(EmailProperties.SSL_PORT, Integer.class));
        mailProperties.setUsername(settingOptionService.getByPropertyOrDefault(EmailProperties.USERNAME, String.class));
        mailProperties.setPassword(settingOptionService.getByPropertyOrDefault(EmailProperties.PASSWORD, String.class));
        mailProperties.setProtocol(settingOptionService.getByPropertyOrDefault(EmailProperties.PROTOCOL, String.class));
        return mailProperties;
    }

    /**
     * Get java mail sender.
     *
     * @return java mail sender
     */
    @NonNull
    private synchronized JavaMailSender getMailSender() {
        MailSenderFactory mailSenderFactory = new MailSenderFactory();
        return mailSenderFactory.getMailSender(getMailProperties());
    }

    private synchronized String getMailFrom(@NonNull JavaMailSender javaMailSender) {
        Assert.notNull(javaMailSender, "Java mail sender must not be null");

        if (javaMailSender instanceof JavaMailSenderImpl) {
            // get user name(email)
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) javaMailSender;
            // build internet address
            return mailSender.getUsername();
        }

        throw new UnsupportedOperationException("Unsupported java mail sender: " + javaMailSender.getClass().getName());
    }

    /**
     * Message callback.
     */
    protected interface Callback {
        /**
         * Handle message set.
         *
         * @param messageHelper mime message helper
         * @throws Exception if something goes wrong
         */
        void handle(@NonNull MimeMessageHelper messageHelper) throws Exception;
    }
}
