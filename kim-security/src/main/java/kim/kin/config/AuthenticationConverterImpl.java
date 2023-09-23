package kim.kin.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class AuthenticationConverterImpl extends ServerFormLoginAuthenticationConverter {
    private ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(AuthenticationConverterImpl.class);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        HttpMethod method = exchange.getRequest().getMethod();
        MediaType contentType = exchange.getRequest().getHeaders().getContentType();
        return exchange.getRequest().getBody().next().flatMap(dataBuffer -> {
            InputStream inputStream = dataBuffer.asInputStream();
//            String resource = new Scanner(inputStream).useDelimiter("\\Z").next();
            try {
                Map<String, String> map = objectMapper.readValue(inputStream, new TypeReference<Map<String, String>>() {
                });
                String username = map.get("username");
                String password = map.get("password");
//                UserDetails userDetails = User.builder().username(username).password(password).authorities("").build();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
                log.info(usernamePasswordAuthenticationToken.toString());
                return Mono.just(usernamePasswordAuthenticationToken);
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
//        return super.convert(exchange);
    }
}
