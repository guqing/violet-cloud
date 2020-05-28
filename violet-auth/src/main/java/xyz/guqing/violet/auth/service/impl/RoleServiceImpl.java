package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.model.entity.system.Role;
import xyz.guqing.violet.auth.mapper.RoleMapper;
import xyz.guqing.violet.auth.service.RoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
