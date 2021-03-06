package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.gateway.enhance.model.entity.BlackList;
import xyz.guqing.violet.gateway.enhance.service.BlackListService;

/**
 * 黑名单
 *
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route/auth/blacklist")
public class BlackListController {

    private final BlackListService blackListService;

    @GetMapping("data")
    public Flux<BlackList> listByPage(PageQuery request, BlackList blackList) {
        return blackListService.findPages(request, blackList);
    }

    @GetMapping("count")
    public Mono<Long> countBlacklist(BlackList blackList) {
        return blackListService.findCount(blackList);
    }

    @GetMapping("exist")
    public Flux<BlackList> listByCondition(String ip, String requestUri, String requestMethod) {
        return blackListService.findByCondition(ip, requestUri, requestMethod);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<BlackList> createBlackList(BlackList blackList) {
        return blackListService.create(blackList);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin')")
    public Mono<BlackList> updateBlackList(BlackList blackList) {
        return blackListService.update(blackList);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<BlackList> deleteBlackList(String ids) {
        return blackListService.delete(ids);
    }

}
