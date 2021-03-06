package xyz.guqing.violet.gateway.enhance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.PageQuery;
import xyz.guqing.violet.gateway.enhance.model.entity.BlockLog;
import xyz.guqing.violet.gateway.enhance.service.BlockLogService;

/**
 * 拦截日志
 *
 * @author guqing
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route/auth/block/logs")
public class BlockLogController {

    private final BlockLogService blockLogService;

    @GetMapping("data")
    public Flux<BlockLog> listByPage(PageQuery request, BlockLog blockLog) {
        return blockLogService.findPages(request, blockLog);
    }

    @GetMapping("count")
    public Mono<Long> countBlockLogs(BlockLog blockLog) {
        return blockLogService.findCount(blockLog);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin')")
    public Flux<BlockLog> deleteBlockLog(String ids) {
        return blockLogService.delete(ids);
    }
}
