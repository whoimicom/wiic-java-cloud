package kim.kin.config.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;


/**
 * issues/1759
 * FIX Webflux base path does not work with Path predicates
 * Invalid contextPath '/${spring.webflux.base-path}': must match the start of requestPath
 * Use:
 * filters:
 *             - StripBasePath=1
 * #            - RewritePath=/$\{spring.webflux.base-path}/(?<segment>/?.*), /$\{segment}
 * #            - RewritePath=/(?<segment>/?.*), /$\{segment}
 * #            - RewritePath=/kim-security/(?<segment>/?.*), /$\{segment}
 * #            - StripPrefix=1
 * #            - PrefixPath=/kim-security
 */
@Component
public class StripBasePathGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Value("${spring.webflux.base-path}")
    private String basePath;

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest req = exchange.getRequest();
            String path = req.getURI().getRawPath();
            String newPath = path.replaceFirst(basePath, "");
            ServerHttpRequest request = req.mutate().path(newPath).contextPath("/").build();
            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}