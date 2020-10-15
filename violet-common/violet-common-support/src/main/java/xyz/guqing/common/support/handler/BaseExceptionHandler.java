package xyz.guqing.common.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.guqing.violet.common.core.exception.*;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author guqing
 */
@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleAccessDeniedException() {
        return ResultEntity.accessDenied("没有权限访问该资源");
    }

    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleAccessDeniedException(ForbiddenException e) {
        log.error("访问权限受限异常,{}", e);
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
        log.error("不支持的请求方法异常: {0}", e);
        return ResultEntity.accessDenied(message);
    }

    @ExceptionHandler(BindSocialAccountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultEntity<String> handleBindSocialAccountException(BindSocialAccountException e) {
        log.error("绑定社交帐号异常:{}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> handleBadRequestException(BadRequestException e) {
        log.error("错误请求：{0}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(BadArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> handleBadArgumentException(BadArgumentException e) {
        log.error("请求参数异常：{0}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultEntity<String> handleAuthFailException(AuthenticationException e) {
        log.error("认证失败，错误信息：{0}", e);
        return ResultEntity.authorizedFailed("认证失败:" + e.getMessage());
    }

    @ExceptionHandler(InvalidGrantException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultEntity<String> handleInvalidGrantException(InvalidGrantException e) {
        log.error("验证oauth2 grant异常：{0}", e);
        return ResultEntity.authorizedFailed(e.getMessage());
    }

    /**
     * 当使用@Valid不带@RequestBody request参数时:
     * 对象验证失败，验证将引发BindException而不是MethodArgumentNotValidException
     * @param e 参数绑定异常
     * @return 返回参数校验失败的错误信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> validExceptionHandler(BindException e){
        // 将错误的参数的详细信息封装到统一的返回实体
        return validParam(e.getBindingResult());
    }

    /**
     * 使用@Valid并且带有@RequestBody request参数时
     * 参数教研失败将抛出MethodArgumentNotValidException异常，由此方法捕获处理
     * @param e 方法参数校验失败的异常
     * @return 返回校验失败错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> validExceptionHandler(MethodArgumentNotValidException e){
        return validParam(e.getBindingResult());
    }

    private ResultEntity<String> validParam(BindingResult bindResult) {
        Assert.notNull(bindResult, "参数校验对象BindingResult不能为空");

        List<FieldError> fieldErrors = bindResult.getFieldErrors();
        Map<String, String> map = new HashMap<>(16);
        fieldErrors.forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        log.error("Parameter verification failed：{}", bindResult.getAllErrors());
        return ResultEntity.badArgumentValue(map);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleException(Exception e) {
        log.error("系统内部异常，{}",e);
        return ResultEntity.serverError(e.getMessage());
    }
}
