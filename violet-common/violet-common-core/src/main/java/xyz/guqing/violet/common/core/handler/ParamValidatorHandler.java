package xyz.guqing.violet.common.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guqing
 * @date 2019-12-21 14:51
 */
@Slf4j
@RestControllerAdvice
public class ParamValidatorHandler {

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
}
