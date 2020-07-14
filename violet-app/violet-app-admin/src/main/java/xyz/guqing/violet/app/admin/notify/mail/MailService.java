package xyz.guqing.violet.app.admin.notify.mail;

import java.io.File;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-07-14
 */
public interface MailService {
    /**
     * Send a simple email
     *
     * @param to      recipient
     * @param subject subject
     * @param content content
     */
    void sendTextMail(String to, String subject, String content);

    /**
     * Send a html mail
     * @param to recipient
     * @param subject subject
     * @param content content
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送带静态资源的邮件其实就是在发送HTML邮件的基础上嵌入静态资源（比如图片
     * 嵌入静态资源的过程和传入附件类似，唯一的区别在于需要标识资源的cid
     * 例如：
     * <html><body>内联图片：<img src='cid:img'/></body></html>
     * helper.addInline("img", file);
     * @param to 接收者
     * @param content 发送内容
     * @param subject 主题
     * @param contentIdMap 资源id参数
     */
    void sendInlineMail(String to, String subject, String content, Map<String, File> contentIdMap);

    /**
     * Send a email with html
     *
     * @param to           recipient
     * @param subject      subject
     * @param content      content
     * @param templateName template name
     */
    void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName);

    /**
     * Send mail with attachments
     *
     * @param to             recipient
     * @param subject        subject
     * @param content        content
     * @param templateName   template name
     * @param attachFilePath attachment full path name
     */
    void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachFilePath);
}
