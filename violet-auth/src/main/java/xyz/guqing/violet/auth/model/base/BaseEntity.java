package xyz.guqing.violet.auth.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 * FieldFill填充策略
 * DEFAULT	默认不处理
 * INSERT	插入填充字段
 * UPDATE	更新填充字段
 * INSERT_UPDATE	插入和更新填充字段
 * @author guqing
 * @date 2020-04-09 13:58
 */
@Data
public class BaseEntity implements Serializable {
    @TableField(value = "id")
    private Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;
}
