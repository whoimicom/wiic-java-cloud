package kim.kin.config;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@Primary
public class AuthenticationManagerImpl implements ReactiveAuthenticationManager {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationManagerImpl.class);
//    @Resource
    private final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Attempts to authenticate the provided Authentication
     *
     * @param authentication – the Authentication to test
     * @return if authentication is successful an Authentication is returned. If authentication cannot be determined, an empty Mono is returned.
     * If authentication fails, a Mono error is returned.
     */
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        // if authentication is successful an Authentication is returned
        if (authentication.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            return Mono.just(authentication);
        }

        // 转换为自定义security令牌
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//        log.debug("{}", token);
//        String name = token.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String password = userDetails.getPassword();
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), password)) {
            throw new BadCredentialsException("用户不存在或者密码错误");
        }
//        token.setAuthenticated(true);
//        token.setDetails(userDetails.getAuthorities());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return Mono.just(usernamePasswordAuthenticationToken);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

}