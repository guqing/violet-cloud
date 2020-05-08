package xyz.guqing.violet.auth.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import xyz.guqing.violet.auth.model.base.BaseEntity;

/**
 * <p>
 * 系统用户社交账户关联表
 * </p>
 *
 * @author guqing
 * @since 2020-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("rms_user_connection")
public class UserConnection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Violet系统用户名
     */
    private String userName;

    /**
     * 第三方平台名称
     */
    private String providerName;

    /**
     * 第三方平台账户ID
     */
    private String providerUserId;

    /**
     * 第三方平台用户名
     */
    private String providerUserName;

    /**
     * 第三方平台昵称
     */
    private String nickName;

    /**
     * 第三方平台头像
     */
    private String imageUrl;

    /**
     * 地址
     */
    private String location;

    /**
     * 备注
     */
    private String remark;
}
