package xyz.guqing.violet.app.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;
import xyz.guqing.violet.app.admin.model.param.ActionLogQuery;
import xyz.guqing.violet.common.core.model.entity.constant.VioletConstant;
import xyz.guqing.violet.common.core.model.entity.system.VioletActionLog;

import java.lang.reflect.Method;

/**
 * 系统操作日志服务类
 * @author guqing
 * @date 2020-06-01
 */
public interface VioletActionLogService extends IService<VioletActionLog> {

    /**
     * 异步保存操作日志
     *
     * @param point     切点
     * @param method    Method
     * @param ip        ip
     * @param operation 操作内容
     * @param username  操作用户
     * @param start     开始时间
     */
    @Async(VioletConstant.ASYNC_POOL)
    void saveLog(ProceedingJoinPoint point, Method method, String ip, String operation, String username, long start);

    /**
     * 根据条件查询日志
     * @param logQuery 查询条件
     * @return 返回分页查询结果
     */
    IPage<VioletActionLog> listBy(ActionLogQuery logQuery);
}
