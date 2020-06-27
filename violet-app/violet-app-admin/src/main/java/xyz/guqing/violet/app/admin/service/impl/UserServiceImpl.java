package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.violet.app.admin.mapper.UserMapper;
import xyz.guqing.violet.app.admin.mapper.UserRoleMapper;
import xyz.guqing.violet.app.admin.model.dto.UserDTO;
import xyz.guqing.violet.app.admin.model.entity.UserDO;
import xyz.guqing.violet.app.admin.model.enums.GenderEnum;
import xyz.guqing.violet.app.admin.model.enums.UserStatusEnum;
import xyz.guqing.violet.app.admin.model.param.UserParam;
import xyz.guqing.violet.app.admin.model.param.UserQuery;
import xyz.guqing.violet.app.admin.service.RoleService;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.entity.system.UserRole;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.common.core.model.support.PageInfo;
import xyz.guqing.violet.common.core.utils.VioletSecurityHelper;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public PageInfo<UserDTO> listByPage(UserQuery userQuery) {
        QueryRequest queryRequest = userQuery.getQueryRequest();
        Long current = queryRequest.getCurrent();
        Long pageSize = userQuery.getQueryRequest().getPageSize();

        PageInfo<UserDTO> pageInfo = new PageInfo<>();
        pageInfo.setCurrent(current);
        pageInfo.setPageSize(pageSize);

        // 没有数据返回空集合分页对象
        Long userCount = countUserBy(userQuery);
        pageInfo.setTotal(userCount);
        if(userCount == 0) {
            pageInfo.setPages(1L);
            pageInfo.setList(Collections.emptyList());
            return pageInfo;
        }

        // 查询
        userQuery.setQueryRequest(queryRequest);
        List<UserDO> userByPage = this.baseMapper.findUserBy(userQuery);
        List<UserDTO> userDtoList = userByPage.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO().convertFrom(user);
                    userDTO.setRoleIds(VioletUtil.commaSeparatedToList(user.getRoleId()));
                    userDTO.setRoleNames(VioletUtil.commaSeparatedToList(user.getRoleName()));
                    return userDTO;
                })
                .collect(Collectors.toList());
        pageInfo.setList(userDtoList);

        // 设置分页数
        Long pageTotal = VioletUtil.getPageTotal(pageSize, userCount);
        pageInfo.setPages(pageTotal);
        return pageInfo;
    }

    @Override
    public Long countUserBy(UserQuery userQuery) {
        return this.baseMapper.countUserBy(userQuery);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserParam userParam) {
        User user = userParam.convertTo();
        user.setStatus(UserStatusEnum.NORMAL.getValue());
        user.setGender(GenderEnum.MALE.getValue());
        //保存用户信息
        save(user);

        // 保存用户角色
        roleService.saveUserRoles(user.getId(), userParam.getRoleIds());
    }

    @Override
    public void updateUser(UserParam userParam) {
        User user = userParam.convertTo();
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
    public void updateAvatar(String username, String avatar) {
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate(User.class);
        updateWrapper.set(User::getAvatar, avatar)
                .eq(User::getUsername, username);
        update(updateWrapper);
    }

    @Override
    public boolean isPresentByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getUsername, username);
        // 不为空即存在返回true
        return Objects.nonNull(getOne(queryWrapper));
    }
}
