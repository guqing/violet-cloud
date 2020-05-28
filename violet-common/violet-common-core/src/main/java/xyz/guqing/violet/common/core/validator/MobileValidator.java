package xyz.guqing.violet.common.core.validator;

import org.apache.commons.lang3.StringUtils;
import xyz.guqing.violet.common.core.annotation.IsMobile;
import xyz.guqing.violet.common.core.model.entity.constant.RegexpConstant;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 * @author guqing
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return VioletUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
