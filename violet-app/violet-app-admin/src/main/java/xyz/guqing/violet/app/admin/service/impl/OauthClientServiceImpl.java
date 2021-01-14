package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
