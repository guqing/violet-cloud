package xyz.guqing.violet.common.core.model.support;

import org.springframework.lang.NonNull;
import xyz.guqing.violet.common.core.utils.BeanUtils;

/**
 * Converter interface for output DTO.
 *
 * <b>The implementation type must be equal to DTO type</b>
 *
 * @param <DTO>    the implementation class type
 * @param <DOMAIN> domain type
 * @author guqing
 * @date 2020-04-04 16:03
 */
public interface OutputConverter<DTO extends OutputConverter<DTO, DOMAIN>, DOMAIN> {

    /**
     * Convert from domain.(shallow)
     *
     * @param domain domain data
     * @return converted dto data
     */
    @SuppressWarnings("unchecked")
    @NonNull
    default <T extends DTO> T convertFrom(@NonNull DOMAIN domain) {

        BeanUtils.updateProperties(domain, this);

        return (T) this;
    }
}
