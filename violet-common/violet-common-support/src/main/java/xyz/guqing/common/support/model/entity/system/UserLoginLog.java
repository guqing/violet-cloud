package xyz.guqing.common.support.model.entity.system;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author guqing
 * @since 2020-05-21
 */
@Data
@Accessors(chain = true)
public class UserLoginLog {

    private static final long serialVersionUID = 1L;

    @TableField(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 登录地点
     */
    private String location;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 操作系统
     */
    @TableField("`system`")
    private String system;

    /**
     * 浏览器
     */
    private String browser;
}
