package xyz.guqing.violet.gateway.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.exception.AuthenticationException;
import xyz.guqing.violet.common.core.exception.BadRequestException;
import xyz.guqing.violet.common.core.exception.ForbiddenException;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guqing
 */
@Slf4j
@RestControllerAdvice
public class GatewayExceptionHandler {
    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<ResultEntity<String>> handleAccessDeniedException() {
        ResultEntity<String> resultEntity = ResultEntity.accessDenied("没有权限访问该资源");
        return Mono.just(resultEntity);
    }

    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Mono<ResultEntity<String>> handleAccessDeniedException(ForbiddenException e) {
        log.error("无权访问,{}", e);
        return Mono.just(ResultEntity.accessDenied("没有权限访问该资源"));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResultEntity<String>> handleBadRequestException(BadRequestException e) {
        log.error("Captured an exception：{0}", e);
        return Mono.just(ResultEntity.badArgument(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Mono<ResultEntity<String>> handleAuthFailException(AuthenticationException e) {
        log.error("认证失败，错误信息：{0}", e);
        return Mono.just(ResultEntity.authorizedFailed("认证失败:" + e.getMessage()));
    }

    /**
     * 当使用@Valid不带@RequestBody request参数时:
     * 对象验证失败，验证将引发BindException而不是MethodArgumentNotValidException
     * @param e 参数绑定异常
     * @return 返回参数校验失败的错误信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResultEntity<String>> validExceptionHandler(BindException e){
        // 将错误的参数的详细信息封装到统一的返回实体
        return Mono.just(validParam(e.getBindingResult()));
    }

    /**
     * 使用@Valid并且带有@RequestBody request参数时
     * 参数教研失败将抛出MethodArgumentNotValidException异常，由此方法捕获处理
     * @param e 方法参数校验失败的异常
     * @return 返回校验失败错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResultEntity<String>> validExceptionHandler(MethodArgumentNotValidException e){
        return Mono.just(validParam(e.getBindingResult()));
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
    public Mono<ResultEntity<String>> handleException(Exception e) {
        log.error("系统内部异常，{}",e);
        return Mono.just(ResultEntity.serverError(e.getMessage()));
    }
}
