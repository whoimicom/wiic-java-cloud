package kim.kin.logs;

import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.ClientRequest.LOG_ID_ATTRIBUTE;

//@Component
//@ConditionalOnMissingBean(name = "outAuthFilter")
public class LogFilter implements OrderedWebFilter {
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public int getOrder() {
        return -200;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String headTraceId = exchange.getRequest().getHeaders().getFirst("id");
//        log.info("获取头的log id .{}", headTraceId);
        final String traceId;
        if (headTraceId == null) {
            traceId = exchange.getAttribute(ServerWebExchange.LOG_ID_ATTRIBUTE);
        } else {
            traceId = headTraceId;
        }
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        exchange.getAttributes().put(LOG_ID_ATTRIBUTE, traceId);
        return chain.filter(exchange).contextWrite(ctx -> {
//            log.info("设置log:{}", traceId);
            return ctx.put("id", traceId);
        });
    }
}

