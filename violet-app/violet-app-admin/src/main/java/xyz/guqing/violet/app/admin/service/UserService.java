package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.param.UserParam;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserService extends IService<User> {
    /**
     * 分页查询用户列表
     * @param userQuery 查询条件
     * @return 返回分页查询结果
     */
    PageInfo<UserDTO> listByPage(UserQuery userQuery);

    /**
     * 统计用户数量，用于分页
     * @param userQuery 查询条件
     * @return 返回统计结果
     */
    Long countUserBy(UserQuery userQuery);

    /**
     * 添加用户
     * @param userParam 用户参数
     */
    void createUser(UserParam userParam);

    /**
     * 根据id用户id更新用户信息和用户角色关系
     * @param userParam 用户信息
     */
    void updateUser(UserParam userParam);

    /**
     * 修改用户头像
     * @param username 用户名
     * @param avatar 头像url
     */
    void updateAvatar(String username, String avatar);
}
