package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;
import xyz.guqing.violet.app.admin.model.params.OauthClientParam;

/**
 * @author guqing
 * @date 2021-01-14
 */
public interface OauthClientService extends IService<OauthClientDetails> {
    /**
     * 查询oauth客户端分页列表数据
     *
     * @param clientId 客户端id
     * @param page     分页参数
     * @return 返回客户端数据列表
     */
    Page<OauthClientDetails> listBy(String clientId, Page<OauthClientDetails> page);

    /**
     * 添加oauth客户端数据
     * 需要先对客户端密钥进行加密在保存
     *
     * @param oauthClientDetails 客户端参数
     */
    void createBy(OauthClientDetails oauthClientDetails);

    /**
     * 根据客户端id查询客户端id是否被占用
     *
     * @param clientId 客户端id
     * @return 如果客户端id已经存在返回 {@code true},否则返回{@code false}
     */
    boolean existByClientId(String clientId);

    /**
     * 根据客户端id更新oauth客户端信息,密钥不允许被修改
     *
     * @param clientId         客户端id
     * @param oauthClientParam 客户端修改参数
     */
    void updateBy(String clientId, OauthClientParam oauthClientParam);
}
