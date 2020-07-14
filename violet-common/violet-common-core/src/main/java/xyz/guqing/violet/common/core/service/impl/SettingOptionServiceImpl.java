package xyz.guqing.violet.common.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.guqing.violet.common.core.exception.MissingPropertyException;
import xyz.guqing.violet.common.core.mapper.SettingOptionMapper;
import xyz.guqing.violet.common.core.model.entity.system.SettingOption;
import xyz.guqing.violet.common.core.model.enums.PropertyEnum;
import xyz.guqing.violet.common.core.service.SettingOptionService;

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
        String[] split = key.split(":");
        // 根据分组和key查询
        LambdaQueryWrapper<SettingOption> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(SettingOption::getGroupName, split[0])
                .eq(SettingOption::getOptionKey,split[1]);
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
}
