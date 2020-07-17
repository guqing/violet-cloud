package xyz.guqing.violet.app.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.violet.app.admin.model.params.UserQuery;
import xyz.guqing.violet.common.core.model.entity.system.User;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据条件查询用户信息
     * @param userQuery 查询条件
     * @return 返回查询结果，查询不到数据返回{@code null}
     */
    List<UserDO> findUserBy(UserQuery userQuery);

    /**
     * 根据条件统计用户数量
     * @param userQuery 查询条件
     * @return 返回用户数量，没有数据返回 0
     */
    Long countUserBy(UserQuery userQuery);
}
