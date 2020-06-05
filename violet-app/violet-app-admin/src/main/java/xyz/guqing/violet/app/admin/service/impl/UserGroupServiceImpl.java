package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.mapper.UserGroupMapper;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    @Override
    public Page<UserGroup> listByPage(String name, QueryRequest queryRequest) {
        Page<UserGroup> page = new Page<>(queryRequest.getCurrent(), queryRequest.getPageSize());

        if(StringUtils.isNotBlank(name)) {
            LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.like(UserGroup::getGroupName, name);
            return page(page,queryWrapper);
        }

        return page(page);
    }
}
