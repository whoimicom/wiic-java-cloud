package kim.kin.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ReactiveHttpContextHolder {
    public static final Class<ServerWebExchange> CONTEXT_KEY = ServerWebExchange.class;

    public static Mono<ServerHttpRequest> getRequest() {
        return Mono.deferContextual(Mono::just).map(contextView -> contextView.get(CONTEXT_KEY).getRequest());
//        return Mono.subscriberContext().map(context -> context.get(CONTEXT_KEY).getRequest());
    }

    //获取当前response
    public static Mono<ServerHttpResponse> getResponse() {
        return Mono.deferContextual(Mono::just).map(contextView -> contextView.get(CONTEXT_KEY).getResponse());
//        return Mono.subscriberContext().map(context -> context.get(CONTEXT_KEY).getResponse());
    }

}