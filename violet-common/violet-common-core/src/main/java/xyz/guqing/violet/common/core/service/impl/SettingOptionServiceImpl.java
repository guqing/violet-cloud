package xyz.guqing.violet.common.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.mapper.SettingOptionMapper;
import xyz.guqing.violet.common.core.model.entity.system.SettingOption;
import xyz.guqing.violet.common.core.service.SettingOptionService;

/**
 * 系统设置服务实现类
 * @author guqing
 * @date 2020-07-14
 */
@Service
public class SettingOptionServiceImpl extends ServiceImpl<SettingOptionMapper, SettingOption> implements SettingOptionService {
}
