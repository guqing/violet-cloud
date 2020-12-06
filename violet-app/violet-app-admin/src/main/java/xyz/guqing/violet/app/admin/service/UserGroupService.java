package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.dto.UserGroupTree;
import xyz.guqing.common.support.model.entity.system.UserGroup;

import java.util.List;

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
     *
     * @param name 用户组名称，非必填
     * @return 返回用户组列表分组
     */
    List<UserGroupTree> listBy(String name);

    /**
     * 创建或更新用户组
     *
     * @param userGroup 用户组参数
     */
    void createOrUpdate(UserGroup userGroup);

    /**
     * 根据用户组id永久删除用户组
     *
     * @param groupIds 待删除用户组id集合
     */
    void deleteByIds(List<Long> groupIds);
}
