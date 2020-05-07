package xyz.guqing.violet.auth.model.params;

import lombok.Data;
import xyz.guqing.violet.common.core.model.support.InputConverter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author guqing
 * @date 2020-5-7
 */
@Data
public class BindUserParam implements Serializable {

    private static final long serialVersionUID = -3890998115990166651L;

    @NotBlank(message = "{required}")
    private String bindUsername;
    @NotBlank(message = "{required}")
    private String bindPassword;
}
