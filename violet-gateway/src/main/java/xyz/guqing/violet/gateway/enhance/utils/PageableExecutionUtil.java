package xyz.guqing.violet.gateway.enhance.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import xyz.guqing.violet.common.core.entity.support.QueryRequest;
import xyz.guqing.violet.common.core.entity.constant.VioletConstant;

/**
 * @author guqing
 */
public class PageableExecutionUtil {

    public static <VIOLET> Flux<VIOLET> getPages(Query query, QueryRequest request, Class<VIOLET> clazz,
                                             ReactiveMongoTemplate template) {
        Sort sort = Sort.by("id").descending();
        if (StringUtils.isNotBlank(request.getField()) && StringUtils.isNotBlank(request.getOrder())) {
            sort = VioletConstant.ORDER_ASC.equals(request.getOrder()) ?
                    Sort.by(request.getField()).ascending() :
                    Sort.by(request.getField()).descending();
        }
        Pageable pageable = PageRequest.of(request.getCurrent(), request.getPageSize(), sort);
        return template.find(query.with(pageable), clazz);
    }
}
