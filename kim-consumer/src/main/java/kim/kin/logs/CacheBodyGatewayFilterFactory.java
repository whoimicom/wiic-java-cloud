package kim.kin.logs;

import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;

//@Component
public class CacheBodyGatewayFilterFactory extends AbstractGatewayFilterFactory<CacheBodyGatewayFilterFactory.CacheBodyConfig> {

    public CacheBodyGatewayFilterFactory() {
        super(CacheBodyConfig.class);
    }

    @Override
    public GatewayFilter apply(CacheBodyConfig config) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++");
        if (config.getRouteId() != null && getPublisher() != null) {
            // send an event to enable caching
            getPublisher().publishEvent(new EnableBodyCachingEvent(this, config.getRouteId()));
        }
        return (exchange, chain) -> chain.filter(exchange);
    }
    public static class CacheBodyConfig implements HasRouteId {

        private String routeId;

        @Override
        public void setRouteId(String routeId) {
            this.routeId = routeId;
        }

        @Override
        public String getRouteId() {
            return this.routeId;
        }
    }
}