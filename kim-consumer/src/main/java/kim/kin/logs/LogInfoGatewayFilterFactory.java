package kim.kin.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.HasRouteId;

/**
 * 日志过滤器工厂类
 * @author ZongZi
 * @date 2021/8/3 3:35 下午
 */
//@Component
public class LogInfoGatewayFilterFactory extends AbstractGatewayFilterFactory<LogInfoGatewayFilterFactory.LogConfig> {

   private static final Logger log = LoggerFactory.getLogger(LogInfoGatewayFilterFactory.class);
   public LogInfoGatewayFilterFactory() {
      super(LogConfig.class);
   }
   @Override
   public GatewayFilter apply(LogConfig logConfig) {
      String routeId = logConfig.getRouteId();
      System.out.println("routeId:"+routeId);
      if (routeId != null && getPublisher() != null) {
         // 将routeId上报，这样AdaptCachedBodyGlobalFilter就可以缓存requestBody
         getPublisher().publishEvent(new EnableBodyCachingEvent(this, routeId));
      }
      return new LogInfoGatewayFilter();
   }

   // 实现HasRouteId，让框架传递路由ID进来。
   public static class LogConfig implements HasRouteId {
      private String routeId;

      @Override
      public String getRouteId() {
         return routeId;
      }

      @Override
      public void setRouteId(String routeId) {
         this.routeId = routeId;
      }
   }
}
