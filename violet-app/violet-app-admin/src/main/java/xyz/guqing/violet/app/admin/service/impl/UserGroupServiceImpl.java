package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import xyz.guqing.common.support.model.entity.system.Menu;
import xyz.guqing.violet.app.admin.mapper.UserGroupMapper;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.common.support.model.dto.UserGroupTree;
import xyz.guqing.common.support.model.entity.system.UserGroup;
import xyz.guqing.violet.app.admin.service.UserService;
import xyz.guqing.violet.common.core.utils.ServiceUtils;
import xyz.guqing.violet.common.core.utils.TreeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    private static final Long TOP_GROUP_ID = 0L;
    private final UserService userService;
    @Override
    public List<UserGroupTree> listBy(String name) {
        LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(UserGroup::getSortIndex)
                .orderByDesc(UserGroup::getCreateTime);

        if(StringUtils.isNotBlank(name)) {
            queryWrapper.like(UserGroup::getGroupName, name);
        }

        List<UserGroup> list = list(queryWrapper);

        List<UserGroupTree> userGroupTrees = convertTo(list);
        // 构建树
        return TreeUtil.build(userGroupTrees);
    }

    private List<UserGroupTree> convertTo(List<UserGroup> userGroups) {
        return ServiceUtils.convertToList(userGroups, userGroup -> {
            UserGroupTree tree = new UserGroupTree();
            tree.setId(userGroup.getId().toString());
            tree.setKey(tree.getId());
            tree.setValue(tree.getId());
            tree.setTitle(userGroup.getGroupName());
            tree.setParentId(userGroup.getParentId().toString());
            return tree;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(UserGroup userGroup) {
        if (userGroup.getParentId() == null) {
            userGroup.setParentId(TOP_GROUP_ID);
        }
        this.saveOrUpdate(userGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<Long> groupIds) {
        removeByIds(groupIds);
        // 清除用户表中组id
        userService.clearUserGroupByGroupIds(groupIds);
    }
}
