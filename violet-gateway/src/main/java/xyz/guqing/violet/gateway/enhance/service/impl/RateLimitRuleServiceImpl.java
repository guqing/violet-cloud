package xyz.guqing.violet.gateway.enhance.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.common.core.utils.DateUtil;
import xyz.guqing.violet.gateway.enhance.model.entity.RateLimitRule;
import xyz.guqing.violet.gateway.enhance.mapper.RateLimitRuleMapper;
import xyz.guqing.violet.gateway.enhance.service.RateLimitRuleService;
import xyz.guqing.violet.gateway.enhance.service.RouteEnhanceCacheService;
import xyz.guqing.violet.gateway.enhance.utils.PageableExecutionUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author guqing
 */
@Service
@RequiredArgsConstructor
public class RateLimitRuleServiceImpl implements RateLimitRuleService {

    private final RouteEnhanceCacheService routeEnhanceCacheService;
    private RateLimitRuleMapper rateLimitRuleMapper;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Autowired(required = false)
    public void setRateLimitRuleMapper(RateLimitRuleMapper rateLimitRuleMapper) {
        this.rateLimitRuleMapper = rateLimitRuleMapper;
    }

    @Autowired(required = false)
    public void setTemplate(ReactiveMongoTemplate template) {
        this.reactiveMongoTemplate = template;
    }

    @Override
    public Flux<RateLimitRule> findAll() {
        return rateLimitRuleMapper.findAll();
    }

    @Override
    public Flux<RateLimitRule> findByRequestUriAndRequestMethod(String requestUri, String requestMethod) {
        return rateLimitRuleMapper.findByRequestUriAndRequestMethod(requestUri, requestMethod);
    }

    @Override
    public Flux<RateLimitRule> findPages(PageQuery request, RateLimitRule rateLimitRule) {
        Query query = getQuery(rateLimitRule);
        return PageableExecutionUtil.getPages(query, request, RateLimitRule.class, reactiveMongoTemplate);
    }

    @Override
    public Mono<Long> findCount(RateLimitRule rateLimitRule) {
        Query query = getQuery(rateLimitRule);
        return reactiveMongoTemplate.count(query, RateLimitRule.class);
    }

    @Override
    public Mono<RateLimitRule> create(RateLimitRule rateLimitRule) {
        rateLimitRule.setCreateTime(DateUtil.formatFullTime(LocalDateTime.now(), DateUtil.FULL_TIME_SPLIT_PATTERN));
        return rateLimitRuleMapper.insert(rateLimitRule)
                .doOnSuccess(routeEnhanceCacheService::saveRateLimitRule);
    }

    @Override
    public Mono<RateLimitRule> update(RateLimitRule rateLimitRule) {
        return this.rateLimitRuleMapper.findById(rateLimitRule.getId())
                .flatMap(r -> {
                    routeEnhanceCacheService.removeRateLimitRule(r);
                    BeanUtils.copyProperties(rateLimitRule, r);
                    return this.rateLimitRuleMapper.save(r);
                }).doOnSuccess(routeEnhanceCacheService::saveRateLimitRule);
    }

    @Override
    public Flux<RateLimitRule> delete(List<String> ids) {
        return rateLimitRuleMapper.deleteByIdIn(ids)
                .doOnNext(routeEnhanceCacheService::removeRateLimitRule);
    }

    private Query getQuery(RateLimitRule rateLimitRule) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(rateLimitRule.getRequestMethod())) {
            criteria.and("requestMethod").is(rateLimitRule.getRequestMethod());
        }
        if (StringUtils.isNotBlank(rateLimitRule.getRequestUri())) {
            criteria.and("requestUri").is(rateLimitRule.getRequestUri());
        }
        if (rateLimitRule.getStatus() != null) {
            criteria.and("status").is(rateLimitRule.getStatus());
        }
        query.addCriteria(criteria);
        return query;
    }
}
