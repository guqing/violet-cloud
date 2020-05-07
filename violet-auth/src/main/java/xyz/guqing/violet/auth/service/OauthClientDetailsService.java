package xyz.guqing.violet.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.auth.entity.OauthClientDetails;
import xyz.guqing.violet.common.core.entity.QueryRequest;

/**
 * @author Yuuki
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    /**
     * 查询（分页）
     *
     * @param request            QueryRequest
     * @param oauthClientDetails oauthClientDetails
     * @return IPage<OauthClientDetails>
     */
    IPage<OauthClientDetails> findOauthClientDetails(QueryRequest request, OauthClientDetails oauthClientDetails);

    /**
     * 根据主键查询
     *
     * @param clientId clientId
     * @return OauthClientDetails
     */
    OauthClientDetails findById(String clientId);

    /**
     * 新增
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void createOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 修改
     *
     * @param oauthClientDetails oauthClientDetails
     */
    void updateOauthClientDetails(OauthClientDetails oauthClientDetails);

    /**
     * 删除
     *
     * @param clientIds clientIds
     */
    void deleteOauthClientDetails(String clientIds);
}
