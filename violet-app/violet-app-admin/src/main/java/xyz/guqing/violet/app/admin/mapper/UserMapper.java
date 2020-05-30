package xyz.guqing.violet.app.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.entity.system.User;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> findUserByPage(UserQuery userQuery);
}
