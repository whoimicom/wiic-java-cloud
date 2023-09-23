package kim.kin.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationSuccessHandlerImpl implements ServerAuthenticationSuccessHandler {


    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.defer(() -> Mono
                .just(webFilterExchange.getExchange().getResponse())
                .flatMap(response -> {
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    // 生成JWT token
                    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//                    Object principal = authentication.getPrincipal();
                    String username = userDetails.getUsername();
                    String tokenStr = jwtTokenUtil.generateToken(username, username, userDetails.getAuthorities());
                    DataBuffer dataBuffer = dataBufferFactory.wrap(tokenStr.getBytes());
                    response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.getHeaders().set(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.toString());
                    response.setStatusCode(HttpStatus.OK);
                    return response.writeWith(Mono.just(dataBuffer));
                }));
    }
}
