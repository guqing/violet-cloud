package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.guqing.violet.app.admin.service.SettingOptionService;
import xyz.guqing.violet.common.core.exception.MissingPropertyException;
import xyz.guqing.violet.app.admin.mapper.SettingOptionMapper;
import xyz.guqing.violet.common.core.model.entity.system.SettingOption;
import xyz.guqing.violet.common.core.model.enums.PropertyEnum;

import java.util.Optional;

/**
 * 系统设置服务实现类
 * @author guqing
 * @date 2020-07-14
 */
@Service
public class SettingOptionServiceImpl extends ServiceImpl<SettingOptionMapper, SettingOption> implements SettingOptionService {
    @Override
    public Object getByKeyOfNullable(String key) {
        return getByKey(key).orElse(null);
    }

    @Override
    public Object getByKeyOfNonNull(String key) {
        return getByKey(key).orElseThrow(() -> new MissingPropertyException("You have to config " + key + " setting"));
    }

    @Override
    public Optional<Object> getByKey(String key) {
        Assert.hasText(key, "Option key must not be blank");
        // 根据分组和key查询
        LambdaQueryWrapper<SettingOption> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SettingOption::getOptionKey, key);
        SettingOption settingOption = getOne(queryWrapper);
        if(settingOption != null) {
            return Optional.of(settingOption.getOptionValue());
        }
        return Optional.empty();
    }

    @Override
    public <T> T getByKeyOrDefault(String key, Class<T> valueType, T defaultValue) {
        return getByKey(key, valueType).orElse(defaultValue);
    }

    @Override
    public <T> Optional<T> getByKey(String key, Class<T> valueType) {
        return getByKey(key).map(value -> PropertyEnum.convertTo(value.toString(), valueType));
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType, T defaultValue) {
        Assert.notNull(property, "Setting property must not be null");

        return getByProperty(property, propertyType).orElse(defaultValue);
    }

    @Override
    public <T> T getByPropertyOrDefault(PropertyEnum property, Class<T> propertyType) {
        return getByProperty(property, propertyType).orElse(property.defaultValue(propertyType));
    }

    @Override
    public <T> Optional<T> getByProperty(PropertyEnum property, Class<T> propertyType) {
        return getByProperty(property).map(propertyValue -> PropertyEnum.convertTo(propertyValue.toString(), propertyType));
    }

    @Override
    public Optional<Object> getByProperty(PropertyEnum property) {
        Assert.notNull(property, "Setting property must not be null");

        return getByKey(property.getValue());
    }
}
