package kim.kin.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LogInfoGatewayFilter implements GatewayFilter {

    private static final Logger log = LoggerFactory.getLogger(LogInfoGatewayFilter.class);

    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBody";

    private List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 获取被全局网关过滤器缓存起来的 requestBody
        DataBuffer cachedRequestBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(cachedRequestBody.asByteBuffer());
        String s = charBuffer.toString();
        System.out.println("--------------");
        System.out.println(s);
        log.error(s);
        System.out.println("--------------");
        return chain.filter(exchange);
    }
}
