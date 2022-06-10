package kim.kin.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * @description:
 * @author: lc
 * @createDate: 2021/2/14
 */
//@Component
public class OutLogFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(OutLogFilter.class);
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    @Override
    public Mono filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set("id", exchange.getAttribute(ServerWebExchange.LOG_ID_ATTRIBUTE));
        };

//        ServerRequest serverRequest = ServerRequest.create(exchange, HandlerStrategies.withDefaults().messageReaders());
//        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
//            //因为约定了终端传参的格式，所以只考虑json的情况，如果是表单传参，请自行发挥
//            System.out.println(body);
//            return Mono.just(body);
//        });
//
//        Flux<DataBuffer> body = exchange.getRequest().getBody();
//        AtomicReference<String> bodyRef = new AtomicReference<>();
//        body.subscribe(buffer -> {
//            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//            DataBufferUtils.release(buffer);
//            bodyRef.set(charBuffer.toString());
//        });
//        System.out.println(bodyRef.toString());

// 需要发送缓存事件才可以获取得了，已封装好CacheBodyGatewayFilterFactory,只需要在filter上加上{CacheBody}即可
        Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        System.out.println(cachedBody);
        if (null != cachedBody) {
            NettyDataBuffer buffer = (NettyDataBuffer) cachedBody;
            System.out.println(buffer.toString(StandardCharsets.UTF_8));
            ;
        }


        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
        exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange).contextWrite(ctx -> {
            log.info("设置log{}", exchange.getLogPrefix());
            return ctx.put("id", exchange.getAttribute(ServerWebExchange.LOG_ID_ATTRIBUTE));
        }).onErrorResume(error -> {
            return deal(exchange).then(Mono.error(error));
        }).then(deal(exchange));
    }

    private Mono deal(ServerWebExchange exchange) {
        return Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
            if (startTime != null) {
                StringBuilder sb = new StringBuilder("请求地址：")
                        .append(exchange.getRequest().getURI().getRawPath())
                        .append(" 耗时: ")
                        .append(System.currentTimeMillis() - startTime)
                        .append("ms");
                DataBuffer dataBuffer = (DataBuffer) exchange.getAttributes()
                        .get(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
                if (dataBuffer != null) {
                    String s = dataBuffer.toString(StandardCharsets.UTF_8);
                    sb.append(" body:").append(s);
                }
                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                ServerHttpResponse response = exchange.getResponse();
                log.info(exchange.getLogPrefix() + sb.toString());
            }
        });
    }

    @Override
    public int getOrder() {
        return 5000;
    }
}