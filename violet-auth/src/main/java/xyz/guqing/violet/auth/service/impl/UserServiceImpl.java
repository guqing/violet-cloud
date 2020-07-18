package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.mapper.UserMapper;
import xyz.guqing.violet.auth.model.dto.UserInfoDTO;
import xyz.guqing.violet.auth.service.MenuService;
import xyz.guqing.violet.common.core.model.dto.CurrentUser;
import xyz.guqing.common.support.model.entity.system.User;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.exception.NotFoundException;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final MenuService menuService;

    @Override
    public CurrentUser loadUserByUsername(String username) {
        Optional<CurrentUser> userOptional = baseMapper.findByUsername(username);
        return userOptional.orElseThrow(() -> new NotFoundException("用户不存在"));
    }

    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        User user = getOne(queryWrapper);
        if(user == null) {
            throw new NotFoundException("用户不存在");
        }
        return user;
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        CurrentUser currentUser = loadUserByUsername(username);
        UserInfoDTO userInfoDTO = convertTo(currentUser);
        String permissions = menuService.findUserPermissions(username);
        List<String> permissionList = VioletUtil.commaSeparatedToList(permissions);
        userInfoDTO.setPermissions(permissionList);
        return userInfoDTO;
    }

    @Override
    public void updateLastLoginTime(String username, LocalDateTime loginTime) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(User::getLastLoginTime, loginTime).eq(User::getUsername, username);
        update(updateWrapper);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getEmail, email);
        return Optional.ofNullable(getOne(queryWrapper));
    }

    private UserInfoDTO convertTo(CurrentUser currentUser) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(currentUser,userInfoDTO);
        userInfoDTO.setRoleIds(VioletUtil.commaSeparatedToList(currentUser.getRoleId()));
        userInfoDTO.setRoleNames(VioletUtil.commaSeparatedToList(currentUser.getRoleName()));
        return userInfoDTO;
    }
}
