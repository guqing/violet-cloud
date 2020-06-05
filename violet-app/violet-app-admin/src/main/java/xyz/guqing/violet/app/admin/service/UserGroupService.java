package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.common.core.model.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.model.entity.support.UserGroupTree;
import xyz.guqing.violet.common.core.model.entity.system.UserGroup;
import xyz.guqing.violet.common.core.model.support.PageInfo;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserGroupService extends IService<UserGroup> {
    /**
     * 分页查询用户组列表
     * @param name 用户组名称，非必填
     * @param queryRequest 分页查询条件
     * @return 返回用户组列表分组
     */
    PageInfo<UserGroupTree> listByPage(String name, QueryRequest queryRequest);
}
