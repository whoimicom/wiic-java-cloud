package kim.kin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@SuppressWarnings("NullableProblems")
@Component
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFilterImpl implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        return chain.filter(exchange).subscriberContext(context -> context.put(ReactiveHttpContextHolder.CONTEXT_KEY, exchange));
        return chain.filter(exchange).contextWrite(context -> context.put(ReactiveHttpContextHolder.CONTEXT_KEY, exchange));
    }
}