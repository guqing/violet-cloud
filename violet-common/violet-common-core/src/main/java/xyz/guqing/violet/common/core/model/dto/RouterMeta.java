package xyz.guqing.violet.common.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Vue路由 Meta
 *
 * @author guqing
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta implements Serializable {

    private static final long serialVersionUID = 5499925008927195914L;

    private String title;
    private String icon;
    private Boolean hidden = false;
    private Boolean keepAlive = false;

}
