package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.common.support.model.entity.system.RoleMenu;
import xyz.guqing.violet.auth.mapper.RoleMenuMapper;
import xyz.guqing.violet.auth.service.RoleMenuService;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
