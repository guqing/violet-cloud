package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.common.support.model.entity.system.OauthClientDetails;

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
}
