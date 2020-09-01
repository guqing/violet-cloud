package xyz.guqing.violet.gateway.enhance.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.model.constant.VioletConstant;

/**
 * @author guqing
 */
@Slf4j
public class PageableExecutionUtil {

    public static <VIOLET> Flux<VIOLET> getPages(Query query, PageQuery request, Class<VIOLET> clazz,
                                                 ReactiveMongoTemplate template) {
        Sort sort = Sort.by("id").descending();
        if (StringUtils.isNotBlank(request.getField()) && StringUtils.isNotBlank(request.getOrder())) {
            sort = VioletConstant.ORDER_ASC.equals(request.getOrder()) ?
                    Sort.by(request.getField()).ascending() :
                    Sort.by(request.getField()).descending();
        }
        // jpa的分页是从0开始的，为了前端统一，则前端传1表示第一页
        Long current = request.getCurrent() - 1L < 0L ? 0L : request.getCurrent() - 1L;
        Long pageSize = request.getPageSize();
        Pageable pageable = PageRequest.of(current.intValue(), pageSize.intValue(), sort);
        return template.find(query.with(pageable), clazz);
    }
}
