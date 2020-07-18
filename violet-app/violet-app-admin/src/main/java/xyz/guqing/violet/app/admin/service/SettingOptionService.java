package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.entity.system.SettingOption;
import xyz.guqing.common.support.model.enums.PropertyEnum;

import java.util.Optional;

/**
 * 系统设置选项服务类
 * @author guqing
 * @date 2020-07-14
 */
public interface SettingOptionService extends IService<SettingOption> {
    /**
     * 获取查询key
     * @param key key,组成方式为 分组名称:key
     * @return 返回查询结果，查询不到返回null
     */
    Object getByKeyOfNullable(String key);

    /**
     * 根据key查询value
     * @param key 组名:key
     * @return 返回查询结果查询不到返回{@code null}
     */
    Object getByKeyOfNonNull(String key);

    /**
     * 根据key查询value
     * @param key 组名:key
     * @return 返回查询结果Optional
     */
    Optional<Object> getByKey(String key);

    /**
     * 根据key查询并返回执行的value类型，如果查询不到返回默认值
     * @param key 组名:key
     * @param valueType 值类型
     * @param defaultValue 查询不到值时的默认值
     * @param <T> 值的泛型
     * @return 返回指定类型的value，查询不到返回默认值
     */
    <T> T getByKeyOrDefault(String key, Class<T> valueType, T defaultValue);

    /**
     * 根据key查询，返回执行类型的value
     * @param key 组名:key
     * @param valueType 指定value的类型
     * @param <T> value类型泛型
     * @return 返回执行类型的value，查询不到返回{@code null}
     */
    <T> Optional<T> getByKey(String key, Class<T> valueType);

    /**
     * 根据枚举属性获取值查询不到返回默认值
     * @param property 枚举属性
     * @param propertyType 属性类型
     * @param defaultValue 默认值
     * @param <T> 返回值泛型
     * @return 返回获取到的值，查询不到返回默认值
     */
    <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType, T defaultValue);

    /**
     * 获取属性
     * @param property 枚举对象
     * @param propertyType 返回值类型
     * @param <T> 返回值类型
     * @return 根据枚举属性返回指定类型的值
     */
    <T> Optional<T> getByProperty(PropertyEnum property, Class<T> propertyType);

    /**
     * 根据枚举属性获取值
     * @param property 枚举属性key
     * @return 返回Optional结果
     */
    Optional<Object> getByProperty(PropertyEnum property);

    /**
     * 根据枚举属性查询结果返回指定类型的值，获取不到返回枚举中给定的默认值
     * @param property 枚举属性
     * @param propertyType 返回值类型
     * @param <T> 返回值类型泛型
     * @return 返回指定类型的结果，查询不到返回枚举中指定的默认值
     */
    <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType);
}
