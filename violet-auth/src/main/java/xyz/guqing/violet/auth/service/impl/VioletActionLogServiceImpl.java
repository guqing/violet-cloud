package xyz.guqing.violet.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.common.core.model.entity.system.VioletActionLog;
import xyz.guqing.violet.auth.mapper.VioletActionLogMapper;
import xyz.guqing.violet.auth.service.VioletActionLogService;

/**
 * <p>
 * 用户操作日志表 服务实现类
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Service
public class VioletActionLogServiceImpl extends ServiceImpl<VioletActionLogMapper, VioletActionLog> implements VioletActionLogService {

}
