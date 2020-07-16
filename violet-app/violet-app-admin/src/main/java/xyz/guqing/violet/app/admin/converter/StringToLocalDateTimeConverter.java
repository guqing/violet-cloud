package xyz.guqing.violet.app.admin.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author guqing
 * @date 2020-07-16
 */
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    /**
     * 因为 Spring 默认不支持将 String 类型的请求参数转换为 LocalDateTime 类型，所以我们需要自定义 converter 「转换器」完整整个转换过程
     */
    @Override
    public LocalDateTime convert(@NonNull String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINESE);
        return LocalDateTime.parse(s, formatter);
    }
}
