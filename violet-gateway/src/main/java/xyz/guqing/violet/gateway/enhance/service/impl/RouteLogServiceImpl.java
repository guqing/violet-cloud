package xyz.guqing.violet.gateway.enhance.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.common.core.utils.DateUtil;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteLog;
import xyz.guqing.violet.gateway.enhance.mapper.RouteLogMapper;
import xyz.guqing.violet.gateway.enhance.service.RouteLogService;
import xyz.guqing.violet.gateway.enhance.utils.PageableExecutionUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author guqing
 */
@Service
public class RouteLogServiceImpl implements RouteLogService {

    private RouteLogMapper routeLogMapper;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired(required = false)
    public void setRouteLogMapper(RouteLogMapper routeLogMapper) {
        this.routeLogMapper = routeLogMapper;
    }

    @Autowired(required = false)
    public void setTemplate(ReactiveMongoTemplate template) {
        this.reactiveMongoTemplate = template;
    }

    @Override
    public Flux<RouteLog> findAll() {
        return routeLogMapper.findAll();
    }

    @Override
    public Mono<RouteLog> create(RouteLog routeLog) {
        routeLog.setCreateTime(DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        return routeLogMapper.insert(routeLog);
    }

    @Override
    public Flux<RouteLog> delete(List<String> ids) {
        return routeLogMapper.deleteByIdIn(ids);
    }

    @Override
    public Flux<RouteLog> findPages(QueryRequest request, RouteLog routeLog) {
        Query query = getQuery(routeLog);
        return PageableExecutionUtil.getPages(query, request, RouteLog.class, reactiveMongoTemplate);
    }

    @Override
    public Mono<Long> findCount(RouteLog routeLog) {
        Query query = getQuery(routeLog);
        return reactiveMongoTemplate.count(query, RouteLog.class);
    }

    private Query getQuery(RouteLog routeLog) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(routeLog.getIp())) {
            criteria.and("ip").is(routeLog.getIp());
        }
        if (StringUtils.isNotBlank(routeLog.getTargetServer())) {
            criteria.and("targetServer").is(routeLog.getTargetServer());
        }
        if (StringUtils.isNotBlank(routeLog.getRequestMethod())) {
            criteria.and("requestMethod").is(routeLog.getRequestMethod().toUpperCase());
        }
        if (StringUtils.isNotBlank(routeLog.getCreateTimeFrom())
                && StringUtils.isNotBlank(routeLog.getCreateTimeTo())) {
            criteria.andOperator(
                    Criteria.where("createTime").gt(routeLog.getCreateTimeFrom()),
                    Criteria.where("createTime").lt(routeLog.getCreateTimeTo())
            );
        }
        query.addCriteria(criteria);
        return query;
    }
}
