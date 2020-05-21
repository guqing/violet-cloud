package xyz.guqing.violet.auth.model.entity;

import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class VioletActionLog extends BaseEntity {

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
     * 耗时
     */
    private BigDecimal time;

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


}
