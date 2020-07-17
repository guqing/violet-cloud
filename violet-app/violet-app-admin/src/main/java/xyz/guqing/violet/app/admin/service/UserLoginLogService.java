package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import xyz.guqing.violet.app.admin.model.params.LoginLogParam;
import xyz.guqing.violet.common.core.model.entity.system.UserLoginLog;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
public interface UserLoginLogService extends IService<UserLoginLog> {
    IPage<UserLoginLog> listBy(LoginLogParam loginLogParam);
}
