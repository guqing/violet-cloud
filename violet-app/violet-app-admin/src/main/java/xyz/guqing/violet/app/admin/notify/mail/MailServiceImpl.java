package xyz.guqing.violet.app.admin.notify.mail;

import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import xyz.guqing.violet.app.admin.service.SettingOptionService;
import xyz.guqing.violet.common.core.exception.EmailException;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;

import javax.mail.MessagingException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-07-14
 */
@Slf4j
@Service
public class MailServiceImpl extends AbstractMailService {
    /**
     * 发送邮件的模板引擎
     */
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public MailServiceImpl(SettingOptionService settingOptionService,
                           FreeMarkerConfigurer freeMarkerConfigurer) {
        super(settingOptionService);
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendTextMail(String to, String subject, String content) {
        sendMailTemplate(messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(content);
        });
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendHtmlMail(String to, String subject, String content) {
        sendMailTemplate(messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(content, true);
        });
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendInlineMail(String to, String subject, String content, Map<String, File> contentIdMap) {
        Assert.notNull(contentIdMap, "Parameter contentIdMap cannot be null");

        sendMailTemplate(messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(content, true);
            contentIdMap.forEach((key, value) -> {
                try {
                    messageHelper.addInline(key, value);
                } catch (MessagingException e) {
                    throw new EmailException(e.getMessage());
                }
            });
        });
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName) {
        sendMailTemplate(messageHelper -> {
            // build message content with freemarker
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            String contentResult = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);

            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            messageHelper.setText(contentResult, true);
        });
    }

    @Override
    @Async(VioletConstant.ASYNC_POOL)
    public void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachFilePath) {
        sendMailTemplate(messageHelper -> {
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);
            Path attachmentPath = Paths.get(attachFilePath);
            messageHelper.addAttachment(attachmentPath.getFileName().toString(), attachmentPath.toFile());
        });
    }
}
