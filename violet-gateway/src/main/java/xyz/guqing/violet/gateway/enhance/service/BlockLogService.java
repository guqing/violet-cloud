package xyz.guqing.violet.gateway.enhance.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.QueryRequest;
import xyz.guqing.violet.gateway.enhance.entity.BlockLog;

/**
 * @author MrBird
 */
public interface BlockLogService {

    /**
     * 创建拦截日志
     *
     * @param blockLog 拦截日志
     * @return 拦截日志
     */
    Mono<BlockLog> create(BlockLog blockLog);

    /**
     * 删除拦截日志
     *
     * @param ids 拦截日志id
     * @return 被删除的拦截日志
     */
    Flux<BlockLog> delete(String ids);

    /**
     * 查找拦截日志分页数据
     *
     * @param request  request
     * @param blockLog blockLog
     * @return 拦截日志分页数据
     */
    Flux<BlockLog> findPages(QueryRequest request, BlockLog blockLog);

    /**
     * 查找拦截日志分页数据count
     *
     * @param blockLog blockLog
     * @return count
     */
    Mono<Long> findCount(BlockLog blockLog);
}
