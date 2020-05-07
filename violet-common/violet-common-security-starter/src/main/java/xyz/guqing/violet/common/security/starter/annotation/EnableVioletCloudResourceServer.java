package xyz.guqing.violet.common.security.starter.annotation;

import org.springframework.context.annotation.Import;
import xyz.guqing.violet.common.security.starter.configure.VioletCloudResourceServerConfigure;

import java.lang.annotation.*;

/**
 * @author guqing
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(VioletCloudResourceServerConfigure.class)
public @interface EnableVioletCloudResourceServer {
}
