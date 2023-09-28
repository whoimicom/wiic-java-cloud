package kim.kin.config;

import jakarta.annotation.Resource;
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
    //    @Resource
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
            return Mono.just(authentication);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        String password = userDetails.getPassword();
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), password)) {
            throw new BadCredentialsException("用户不存在或者密码错误");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        return Mono.just(usernamePasswordAuthenticationToken);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

}