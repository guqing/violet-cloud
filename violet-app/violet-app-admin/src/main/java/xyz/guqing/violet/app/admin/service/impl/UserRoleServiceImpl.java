package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.mapper.UserRoleMapper;
import xyz.guqing.violet.app.admin.service.UserRoleService;
import xyz.guqing.common.support.model.entity.system.UserRole;

import java.util.List;

/**
 * @author guqing
 * @date 2020-06-16
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(UserRole::getRoleId, roleIds);
        remove(queryWrapper);
    }
}
