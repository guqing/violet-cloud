package xyz.guqing.violet.common.core.model.entity.system;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户操作日志表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@Accessors(chain = true)
public class VioletActionLog {

    private static final long serialVersionUID = 1L;

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
