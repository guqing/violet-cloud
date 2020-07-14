package xyz.guqing.violet.common.core.notify.model;

import lombok.Data;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author guqing
 * @date 2020-07-14
 */
@Data
public class MailProperties {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String protocol = "smtp";
    private Charset defaultEncoding = DEFAULT_CHARSET;
}
