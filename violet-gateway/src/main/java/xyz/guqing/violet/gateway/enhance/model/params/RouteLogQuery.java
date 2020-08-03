package xyz.guqing.violet.gateway.enhance.model.params;

import lombok.Data;

/**
 * @author guqing
 * @date 2020-08-03
 */
@Data
public class RouteLogQuery {
    private String ip;
    private String targetUri;
    private String targetServer;
    private String requestMethod;
    private String createTimeFrom;
    private String createTimeTo;
}
