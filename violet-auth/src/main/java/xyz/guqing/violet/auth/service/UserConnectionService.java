package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import me.zhyd.oauth.model.AuthUser;
import xyz.guqing.violet.auth.model.entity.UserConnection;

/**
 * <p>
 * 系统用户社交账户关联表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserConnectionService extends IService<UserConnection> {
    /**
     * 根据第三方登录识别码和用户uuid查询第三方帐号信息
     * @param source 第三方识别码
     * @param uuid 用户唯一标识
     * @return 如果查询返回数据，否则抛出NotFoundException
     */
    UserConnection getBySourceAndUuid(String source, String uuid);

    /**
     * 创建第三方登录帐号
     * @param username 用户名
     * @param authUser 第三方登录用户信息
     */
    void create(String username, AuthUser authUser);
}
