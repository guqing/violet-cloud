package xyz.guqing.violet.app.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.violet.app.admin.model.params.UserQuery;
import xyz.guqing.common.support.model.entity.system.User;

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
     *
     * @param userQuery 查询条件
     * @param page      分页
     * @return 返回分页page对象
     */
    Page<UserDO> findUserBy(@Param("q") UserQuery userQuery, Page<UserDO> page);
}
