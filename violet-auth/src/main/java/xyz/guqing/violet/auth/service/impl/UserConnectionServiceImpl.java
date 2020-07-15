package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.model.entity.system.UserConnection;
import xyz.guqing.violet.auth.mapper.UserConnectionMapper;
import xyz.guqing.violet.auth.service.UserConnectionService;

import java.util.List;

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

    @Override
    public UserConnection getBySourceAndUuid(String source, String uuid) {
        LambdaQueryWrapper<UserConnection> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserConnection::getProviderName, source)
                .eq(UserConnection::getProviderUserId, uuid);
        return getOne(queryWrapper);
    }

    @Override
    public void create(Long userId, AuthUser authUser) {
        UserConnection userConnection = new UserConnection();
        userConnection.setUserId(userId);
        userConnection.setProviderName(authUser.getSource().toString());
        userConnection.setProviderUserId(authUser.getUuid());
        userConnection.setProviderUserName(authUser.getUsername());
        userConnection.setAvatar(authUser.getAvatar());
        userConnection.setNickName(authUser.getNickname());
        userConnection.setLocation(authUser.getLocation());

        save(userConnection);
    }

    @Override
    public List<UserConnection> listByUserId(Long userId) {
        LambdaQueryWrapper<UserConnection> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserConnection::getUserId, userId);
        return list(queryWrapper);
    }
}
