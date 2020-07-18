package xyz.guqing.violet.common.core.model.support;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.guqing.violet.common.core.model.enums.ResultEntityEnum;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author guqing
 * @date 2019-8-9
 */
@Data
@AllArgsConstructor
public class ResultEntity<T> {
    private String code;
    private String message;
    private T data;

    public boolean isSuccess() {
        return Objects.equals(ResultEntityEnum.SUCCESS.getCode(), this.code);
    }

    public static ResultEntity<String> ok() {
        return new ResultEntity<>(ResultEntityEnum.SUCCESS.getCode(),
                ResultEntityEnum.SUCCESS.getDesc(), "");
    }

    public static<T> ResultEntity<T> ok(T data) {
        return new ResultEntity<>(ResultEntityEnum.SUCCESS.getCode(),
                ResultEntityEnum.SUCCESS.getDesc(), data);
    }

    public static<T, DTO> ResultEntity<PageInfo<DTO>> okList(List<T> list, Function<T, DTO> function) {
        return ok(PageInfo.convertFrom(list, function));
    }

    public static ResultEntity<String> fail() {
        return new ResultEntity<>(ResultEntityEnum.SERVER_ERROR.getCode(),
                ResultEntityEnum.SERVER_ERROR.getDesc(), "");
    }

    private static<T> ResultEntity<T> fail(String code, String message, T data) {
        return new ResultEntity<>(code, message, data);
    }

    private static ResultEntity<String> fail(String code, String message) {
        return fail(code, message, "");
    }

    public static ResultEntity<String> badArgument() {
        ResultEntityEnum badArgument = ResultEntityEnum.BAD_ARGUMENT;
        return fail(badArgument.getCode(), badArgument.getDesc());
    }

    public static ResultEntity<String> badArgument(String message) {
        return fail(ResultEntityEnum.BAD_ARGUMENT.getCode(),
                ResultEntityEnum.BAD_ARGUMENT_VALUE.getDesc());
    }

    public static ResultEntity<String> badArgumentValue(Map<String, String> validMap) {
        String message = JSONObject.toJSONString(validMap);
        return fail(ResultEntityEnum.BAD_ARGUMENT_VALUE.getCode(),
                ResultEntityEnum.BAD_ARGUMENT_VALUE.getDesc(), message);
    }

    public static ResultEntity<String> badArgumentValue() {
        return fail(ResultEntityEnum.BAD_ARGUMENT_VALUE.getCode(),
                ResultEntityEnum.BAD_ARGUMENT_VALUE.getDesc());
    }

    public static ResultEntity<String> userPasswordError() {
        return fail(ResultEntityEnum.USER_PASSWORD_ERROR.getCode(),
                ResultEntityEnum.USER_PASSWORD_ERROR.getDesc());
    }

    public static ResultEntity<String> userNotFound() {
        return fail(ResultEntityEnum.USER_NOT_FOUND.getCode(),
                ResultEntityEnum.USER_NOT_FOUND.getDesc());
    }

    public static ResultEntity<String> executionTimeout(String message) {
        return fail(ResultEntityEnum.SERVER_EXECUTION_TIMEOUT.getCode(), message);
    }

    public static ResultEntity<String> authorizedFailed(String message) {
        return fail(ResultEntityEnum.CERTIFICATE_VERIFICATION_FAILED.getCode(), message);
    }

    public static ResultEntity<String> loginFailed(String message) {
        return fail(ResultEntityEnum.USER_LOGIN_EXCEPTION.getCode(), message);
    }

    public static ResultEntity<String> accessDenied(String message) {
        return fail(ResultEntityEnum.ACCESS_DENIED.getCode(), message);
    }

    public static ResultEntity<String> dataNotFound(String data) {
        return fail(ResultEntityEnum.USER_RESOURCE_EXCEPTION.getCode(),
                ResultEntityEnum.USER_RESOURCE_EXCEPTION.getDesc(), data);
    }

    public static ResultEntity<String> serverError() {
        return fail(ResultEntityEnum.SERVER_ERROR.getCode(),
                ResultEntityEnum.SERVER_ERROR.getDesc());
    }

    public static ResultEntity<String> unSupportedMediaType() {
        return fail(ResultEntityEnum.UN_SUPPORTED_MEDIA_TYPE.getCode(),
                ResultEntityEnum.UN_SUPPORTED_MEDIA_TYPE.getDesc());
    }

    public static ResultEntity<String> uploadError() {
        return fail(ResultEntityEnum.USER_UPLOAD_ERROR.getCode(),
                ResultEntityEnum.USER_UPLOAD_ERROR.getDesc());
    }

    public static ResultEntity<String> unauthorized() {
        return fail(ResultEntityEnum.UNAUTHORIZED.getCode(),
                ResultEntityEnum.UNAUTHORIZED.getDesc());
    }

    public static ResultEntity<String> rpcFailed() {
        return fail(ResultEntityEnum.RPC_FAILED.getCode(),
                ResultEntityEnum.RPC_FAILED.getDesc());
    }

    public static ResultEntity<String> repeatOps() {
        return fail(ResultEntityEnum.REPEAT_OPS.getCode(),
                ResultEntityEnum.REPEAT_OPS.getDesc());
    }

    public static ResultEntity<String> notificationError() {
        return fail(ResultEntityEnum.NOTIFICATION_ERROR.getCode(),
                ResultEntityEnum.NOTIFICATION_ERROR.getDesc());
    }
}
