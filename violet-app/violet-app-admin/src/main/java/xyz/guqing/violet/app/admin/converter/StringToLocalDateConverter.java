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
public class StringToLocalDateConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(@NonNull String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINESE);
        return LocalDateTime.parse(s, formatter);
    }
}
