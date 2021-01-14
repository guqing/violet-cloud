package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.app.admin.mapper.OauthClientDetailsMapper;
import xyz.guqing.violet.app.admin.service.OauthClientService;

/**
 * @author guqing
 * @date 2021-01-14
 */
@Service
public class OauthClientServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientService {

    @Override
    public Page<OauthClientDetails> listBy(String clientId, Page<OauthClientDetails> page) {
        if (StringUtils.isNotBlank(clientId)) {
            LambdaQueryWrapper<OauthClientDetails> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.like(OauthClientDetails::getClientId, clientId);
            return page(page, queryWrapper);
        }
        return page(page);
    }
}
