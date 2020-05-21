package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.UserConnection;
import xyz.guqing.violet.auth.model.mapper.UserConnectionMapper;
import xyz.guqing.violet.auth.service.UserConnectionService;

/**
 * <p>
 * 系统用户社交账户关联表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserConnectionServiceImpl extends ServiceImpl<UserConnectionMapper, UserConnection> implements UserConnectionService {

}
