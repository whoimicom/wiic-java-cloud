package kim.kin.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @calssName AppCacheRequestBodyFilter
 * @Description 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
 * @Author jiangshaoneng
 * @DATE 2020/9/27 14:42
 */
//@Component
public class GlobalCacheRequestBodyFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(GlobalCacheRequestBodyFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("GlobalCacheRequestBodyFilter request body 缓存拦截器生效...");
        // 将 request body 中的内容 copy 一份，记录到 exchange 的一个自定义属性中
        Object cachedRequestBodyObject = exchange.getAttributeOrDefault(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR, null);
        // 如果已经缓存过，略过
        if (cachedRequestBodyObject != null) {
            return chain.filter(exchange);
        }
        // 如果没有缓存过，获取字节数组存入 exchange 的自定义属性中
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
//                    System.out.println(bytes);
                    return bytes;
                }).defaultIfEmpty(new byte[0])
                .doOnNext(bytes -> exchange.getAttributes().put(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR, bytes))
                .then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return -100000;
    }
}
 