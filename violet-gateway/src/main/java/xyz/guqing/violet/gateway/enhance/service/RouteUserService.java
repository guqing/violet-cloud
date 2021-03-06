package xyz.guqing.violet.gateway.enhance.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.gateway.enhance.model.entity.RouteUser;

import java.util.List;

/**
 * @author guqing
 */
public interface RouteUserService {

    /**
     * 创建路由用户
     *
     * @param user 路由用户
     * @return 路由用户
     */
    Mono<RouteUser> create(RouteUser user);

    /**
     * 更新路由用户
     *
     * @param routeUser 路由用户
     * @return 路由用户
     */
    Mono<RouteUser> update(RouteUser routeUser);

    /**
     * 删除路由用户
     *
     * @param ids 路由用户id
     * @return 被删除的路由用户
     */
    Flux<RouteUser> delete(List<String> ids);

    /**
     * 根据用户名获取路由用户
     *
     * @param username 用户名
     * @return 路由用户
     */
    Mono<RouteUser> findByUsername(String username);

    /**
     * 查找路由用户分页数据
     *
     * @param request   request
     * @param routeUser routeUser
     * @return 路由用户分页数据
     */
    Flux<RouteUser> findPages(PageQuery request, RouteUser routeUser);

    /**
     * 查找路由用户分页数据count
     *
     * @param routeUser routeUser
     * @return count
     */
    Mono<Long> findCount(RouteUser routeUser);
}
