package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.app.admin.mapper.OauthClientDetailsMapper;
import xyz.guqing.violet.app.admin.model.params.OauthClientParam;
import xyz.guqing.violet.app.admin.service.OauthClientService;
import xyz.guqing.violet.common.core.exception.NotFoundException;

/**
 * @author guqing
 * @date 2021-01-14
 */
@Service
@RequiredArgsConstructor
public class OauthClientServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<OauthClientDetails> listBy(String clientId, Page<OauthClientDetails> page) {
        if (StringUtils.isNotBlank(clientId)) {
            LambdaQueryWrapper<OauthClientDetails> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.like(OauthClientDetails::getClientId, clientId);
            return page(page, queryWrapper);
        }
        return page(page);
    }

    @Override
    public void createBy(OauthClientDetails oauthClientDetails) {
        String encodedSecret = passwordEncoder.encode(oauthClientDetails.getClientSecret());
        oauthClientDetails.setClientSecret(encodedSecret);
        save(oauthClientDetails);
    }

    @Override
    public boolean existByClientId(String clientId) {
        OauthClientDetails oauthClientDetails = getById(clientId);
        return oauthClientDetails != null;
    }

    @Override
    public void updateBy(String clientId, OauthClientParam oauthClientParam) {
        OauthClientDetails oauthClientDetails = getById(clientId);
        if(oauthClientDetails == null) {
            throw new NotFoundException("客户端数据不存在");
        }
        oauthClientParam.update(oauthClientDetails);
        updateById(oauthClientDetails);
    }
}
