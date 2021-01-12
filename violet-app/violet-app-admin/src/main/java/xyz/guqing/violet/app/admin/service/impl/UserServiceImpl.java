package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.guqing.common.support.utils.PageUtils;
import xyz.guqing.violet.app.admin.mapper.UserMapper;
import xyz.guqing.violet.app.admin.mapper.UserRoleMapper;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.common.support.model.enums.GenderEnum;
import xyz.guqing.common.support.model.enums.UserStatusEnum;
import xyz.guqing.violet.app.admin.model.params.UserParam;
import xyz.guqing.violet.app.admin.model.params.UserQuery;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.exception.BadArgumentException;
import xyz.guqing.violet.common.core.exception.NotFoundException;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;
import xyz.guqing.common.support.model.entity.system.UserRole;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.common.support.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.utils.ServiceUtils;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RoleService roleService;
    private final UserRoleMapper userRoleMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageInfo<UserDTO> listByPage(UserQuery userQuery, PageQuery pageQuery) {
        Page<UserDO> userByPage = this.baseMapper.findUserBy(userQuery, PageUtils.convert(pageQuery));
        List<UserDTO> userDtoList = ServiceUtils.convertToList(userByPage.getRecords(), user -> {
            UserDTO userDTO = new UserDTO().convertFrom(user);
            userDTO.setRoleIds(VioletUtil.commaSeparatedToList(user.getRoleId()));
            userDTO.setRoleNames(VioletUtil.commaSeparatedToList(user.getRoleName()));
            return userDTO;
        });

        return PageUtils.convertTo(userByPage, userDtoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserParam userParam) {
        User user = userParam.convertTo();
        user.setNickname(user.getUsername());
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setGender(GenderEnum.MALE.getValue());

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //保存用户信息
        save(user);

        // 保存用户角色
        roleService.saveUserRoles(user.getId(), userParam.getRoleIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserParam userParam) {
        User user = userParam.convertTo();
        // 加密密码
        String password = user.getPassword();
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }

        this.updateById(user);

        // 更新用户角色,先删除在插入
        LambdaQueryWrapper<UserRole> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserRole::getUserId, user.getId());
        userRoleMapper.delete(queryWrapper);
        List<Long> roleIds = userParam.getRoleIds();
        // 插入
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleMapper.insert(userRole);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String username, String avatar) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class);
        updateWrapper.set(User::getAvatar, avatar)
                .eq(User::getUsername, username);
        update(updateWrapper);
    }

    @Override
    public boolean isPresentByUsername(String username) {
        // 不为空即存在返回true
        return Objects.nonNull(getByUsername(username));
    }

    @Override
    public boolean isPresentByEmail(String email) {
        User user = getByEmail(email);
        return Objects.nonNull(user);
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearUserGroupByGroupIds(List<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getGroupId, null)
                .in(User::getGroupId, groupIds);
        update(updateWrapper);
    }

    private User getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getEmail, email);
        return getOne(queryWrapper);
    }

    @Override
    public boolean isCorrectByPassword(String username, String password) {
        User user = getByUsername(username);
        // 直接匹配，无需先加密
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String username) {
        User user = getByUsername(username);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        String defaultPassword = passwordEncoder.encode(VioletConstant.DEFAULT_PASSWORD);
        user.setPassword(defaultPassword);

        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String username, UserStatusEnum status) {
        User user = getByUsername(username);
        if (user == null) {
            throw new NotFoundException("用户不存在");
        }
        user.setStatus(status.getValue());
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeByUserNames(List<String> usernames) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(User::getUsername, usernames);
        remove(queryWrapper);
    }

    @Override
    public void updatePassword(String username, String oldPassword, String newPassword) {
        boolean correctByPassword = isCorrectByPassword(username, oldPassword);
        if (!correctByPassword) {
            throw new BadArgumentException("原始密码不正确");
        }
        // 修改密码
        String encodedPassword = passwordEncoder.encode(newPassword);
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getPassword, encodedPassword)
                .eq(User::getUsername, username);
        update(updateWrapper);
    }
}
