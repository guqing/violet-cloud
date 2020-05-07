package xyz.guqing.violet.common.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.guqing.violet.common.core.exception.AuthenticationException;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.ForbiddenException;
import xyz.guqing.violet.common.core.model.support.ResultEntity;


/**
 * @author guqing
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleException(Exception e) {
        log.error("系统内部异常，{}",e);
        return ResultEntity.serverError();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleAccessDeniedException() {
        return ResultEntity.accessDenied("没有权限访问该资源");
    }

    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleAccessDeniedException(ForbiddenException e) {
        log.error("无权访问,{}", e);
        return ResultEntity.accessDenied("没有权限访问该资源");
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持的媒体类型异常,{}", e);
        return ResultEntity.unSupportedMediaType();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = "该方法不支持" + StringUtils.substringBetween(e.getMessage(), "'", "'") + "请求方法";
        log.error(message);
        return ResultEntity.accessDenied(message);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> handleBadRequestException(BadRequestException e) {
        log.error("Captured an exception：{0}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultEntity<String> handleAuthFailException(AuthenticationException e) {
        log.error("认证失败，错误信息：{0}", e);
        return ResultEntity.authorizedFailed("认证失败:" + e.getMessage());
    }

}
