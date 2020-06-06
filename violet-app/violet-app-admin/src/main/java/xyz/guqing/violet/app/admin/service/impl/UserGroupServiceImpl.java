package xyz.guqing.violet.app.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.guqing.violet.app.admin.mapper.UserGroupMapper;
import xyz.guqing.violet.app.admin.service.UserGroupService;
import xyz.guqing.violet.common.core.model.dto.UserGroupTree;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    private static final Long TOP_GROUP_ID = 0L;
    @Override
    public List<UserGroupTree> listBy(String name) {
        LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(UserGroup::getCreateTime);

        if(StringUtils.isNotBlank(name)) {
            queryWrapper.like(UserGroup::getGroupName, name);
        }

        List<UserGroup> list = list(queryWrapper);
        if(CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }

        // 构建树
        List<UserGroupTree> userGroupTrees = new LinkedList<>();
        buildTrees(userGroupTrees, list);

        return userGroupTrees;
    }

    private void buildTrees(List<UserGroupTree> trees, List<UserGroup> userGroups) {
        userGroups.forEach(userGroup -> {
            UserGroupTree tree = new UserGroupTree();
            tree.setId(userGroup.getId().toString());
            tree.setKey(tree.getId());
            tree.setValue(tree.getId());
            tree.setTitle(userGroup.getGroupName());
            tree.setParentId(userGroup.getParentId().toString());
            trees.add(tree);
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
}
