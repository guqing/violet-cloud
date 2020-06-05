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
import xyz.guqing.violet.common.core.model.entity.support.UserGroupTree;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;
import xyz.guqing.violet.common.core.model.support.PageInfo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author guqing
 * @date 2020-06-05
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    @Override
    public PageInfo<UserGroupTree> listByPage(String name, QueryRequest queryRequest) {
        Page<UserGroup> page = new Page<>(queryRequest.getCurrent(), queryRequest.getPageSize());

        LambdaQueryWrapper<UserGroup> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(UserGroup::getCreateTime);

        if(StringUtils.isNotBlank(name)) {
            queryWrapper.like(UserGroup::getGroupName, name);
        }

        Page<UserGroup> userGroupPage = page(page, queryWrapper);

        // 构建树
        List<UserGroupTree> userGroupTrees = new LinkedList<>();
        buildTrees(userGroupTrees, userGroupPage.getRecords());

        return getPageInfo(userGroupPage, userGroupTrees);
    }

    private PageInfo<UserGroupTree> getPageInfo(Page<UserGroup> userGroupPage, List<UserGroupTree> userGroupTrees) {
        PageInfo<UserGroupTree> pageInfo = new PageInfo<>();
        pageInfo.setPages(userGroupPage.getPages());
        pageInfo.setTotal(userGroupPage.getTotal());
        pageInfo.setCurrent(userGroupPage.getCurrent());
        pageInfo.setPageSize(userGroupPage.getSize());
        return pageInfo;
    }

    private void buildTrees(List<UserGroupTree> trees, List<UserGroup> userGroups) {
        userGroups.forEach(userGroup -> {
            UserGroupTree tree = new UserGroupTree();
            tree.setId(userGroup.getId().toString());
            tree.setParentId(userGroup.getParentId().toString());
            tree.setTitle(userGroup.getGroupName());
            trees.add(tree);
        });
    }
}
