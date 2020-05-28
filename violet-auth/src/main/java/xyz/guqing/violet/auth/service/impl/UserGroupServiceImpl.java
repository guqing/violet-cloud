package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;
import xyz.guqing.violet.auth.mapper.UserGroupMapper;
import xyz.guqing.violet.auth.service.UserGroupService;

/**
 * <p>
 * 用户分组表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

}
