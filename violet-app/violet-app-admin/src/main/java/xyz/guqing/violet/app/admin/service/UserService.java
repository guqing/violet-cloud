package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.common.support.model.enums.UserStatusEnum;
import xyz.guqing.violet.app.admin.model.params.UserParam;
import xyz.guqing.violet.app.admin.model.params.UserQuery;
import xyz.guqing.common.support.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.model.support.PageQuery;

import javax.validation.constraints.NotNull;
import java.util.List;

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
     *
     * @param userQuery 查询条件
     * @param pageQuery 分页参数
     * @return 返回分页查询结果
     */
    PageInfo<UserDTO> listByPage(UserQuery userQuery, PageQuery pageQuery);

    /**
     * 添加用户
     *
     * @param userParam 用户参数
     */
    void createUser(UserParam userParam);

    /**
     * 根据id用户id更新用户信息和用户角色关系
     *
     * @param userParam 用户信息
     */
    void updateUser(UserParam userParam);

    /**
     * 修改用户头像
     *
     * @param username 用户名
     * @param avatar   头像url
     */
    void updateAvatar(String username, String avatar);

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 用户名已经存在返回{@code true},否则返回{@code false}
     */
    boolean isPresentByUsername(String username);

    /**
     * 判断邮箱是否已经被绑定
     *
     * @param email 邮箱地址
     * @return 如果邮箱地址已经被绑定则返回{@code true},否则返回{@code false}
     */
    boolean isPresentByEmail(String email);

    /**
     * 判断用户密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果正确返回{@code true},否则返回{@code false}
     */
    boolean isCorrectByPassword(@NotNull String username, @NotNull String password);

    /**
     * 重置用户密码
     *
     * @param username 用户名
     */
    void resetPassword(@NotNull String username);

    /**
     * 更新用户状态
     *
     * @param username 用户名
     * @param status   用户状态
     */
    void updateStatus(@NotNull String username, UserStatusEnum status);

    /**
     * 根据用户名集合批量删除用户
     *
     * @param userNames 用户名集合
     */
    void removeByUserNames(List<String> userNames);

    /**
     * 修改用户密码
     *
     * @param username    用户名
     * @param oldPassword 原始密码
     * @param newPassword 新密码
     */
    void updatePassword(String username, String oldPassword, String newPassword);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 如果查询到返回用户信息, 查询不到返回null
     */
    User getByUsername(String username);

    /**
     * 清空用户组所属关系
     *
     * @param groupIds 分组id集合
     */
    void clearUserGroupByGroupIds(List<Long> groupIds);
}
