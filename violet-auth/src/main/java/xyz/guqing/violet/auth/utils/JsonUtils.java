package xyz.guqing.violet.auth.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * json工具类包装，为了以后切换回jackson方便，只需要修改实现即可
 * @author guqing
 * @date 2020-05-28
 */
public class JsonUtils {
    public static<T> T parseObject(String text, Class<T> clazz) {
        return JSONObject.parseObject(text, clazz);
    }

    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object);
    }
}
