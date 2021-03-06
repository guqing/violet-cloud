package xyz.guqing.violet.app.admin.model.dto;

import lombok.Data;
import xyz.guqing.common.support.model.entity.system.VioletActionLog;
import xyz.guqing.violet.common.core.model.support.OutputConverter;

import java.time.LocalDateTime;

/**
 * @author guqing
 * @date 2020-07-11
 */
@Data
public class ActionLogDTO implements OutputConverter<ActionLogDTO, VioletActionLog> {
    private Long id;
    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作内容
     */
    private String operation;

    /**
     * 执行时间，单位毫秒
     */
    private Long executionTime;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作者IP
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
