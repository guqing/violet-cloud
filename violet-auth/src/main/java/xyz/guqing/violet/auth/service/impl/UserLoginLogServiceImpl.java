package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.model.entity.UserLoginLog;
import xyz.guqing.violet.auth.model.mapper.UserLoginLogMapper;
import xyz.guqing.violet.auth.service.UserLoginLogService;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {

}
