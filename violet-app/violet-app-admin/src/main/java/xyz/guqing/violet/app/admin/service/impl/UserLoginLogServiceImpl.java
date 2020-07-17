package xyz.guqing.violet.app.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.app.admin.mapper.UserLoginLogMapper;
import xyz.guqing.violet.app.admin.model.params.LoginLogParam;
import xyz.guqing.violet.app.admin.service.UserLoginLogService;
import xyz.guqing.violet.common.core.model.entity.system.UserLoginLog;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-17
 */
@Slf4j
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {
    @Override
    public IPage<UserLoginLog> listBy(LoginLogParam loginLogParam) {
        log.debug("列表查询参数:{}", JSONObject.toJSONString(loginLogParam));

        LambdaQueryWrapper<UserLoginLog> queryWrapper = Wrappers.lambdaQuery();

        Integer current = loginLogParam.getCurrent();
        if (current == null) {
            current = 1;
        }

        Integer pageSize = loginLogParam.getPageSize();
        if (pageSize == null) {
            pageSize = 10;
        }

        String username = loginLogParam.getUsername();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(UserLoginLog::getUsername, username);
        }

        LocalDateTime createFrom = loginLogParam.getCreateFrom();
        LocalDateTime createTo = loginLogParam.getCreateTo();
        if (createFrom != null && createTo != null) {
            queryWrapper.ge(UserLoginLog::getLoginTime, createFrom)
                    .le(UserLoginLog::getLoginTime, createTo);
        }

        return page(new Page<>(current, pageSize), queryWrapper);
    }
}