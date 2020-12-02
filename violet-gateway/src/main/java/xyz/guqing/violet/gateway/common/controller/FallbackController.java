package xyz.guqing.violet.gateway.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import xyz.guqing.violet.common.core.model.support.ResultEntity;

/**
 * @author guqing
 */
@Slf4j
@RestController
public class FallbackController {

    @RequestMapping("fallback/{name}")
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public Mono<ResultEntity<String>> systemFallback(@PathVariable String name) {
        String response = "服务访问超时，请稍后再试";
        log.error("[{}]，目标微服务：[{}]", response, name);
        return Mono.just(ResultEntity.executionTimeout(response));
    }

}
