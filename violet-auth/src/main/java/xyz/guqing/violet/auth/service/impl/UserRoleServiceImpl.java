package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.UserRole;
import xyz.guqing.violet.auth.model.mapper.UserRoleMapper;
import xyz.guqing.violet.auth.service.UserRoleService;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
