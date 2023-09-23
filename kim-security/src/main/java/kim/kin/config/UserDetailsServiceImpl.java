package kim.kin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

/*    private AppUserRepository appUserRepository;


    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }*/


/*    @Override
    public Mono<UserDetails> findByUsername(String username) throws UsernameNotFoundException {
        Mono<AppUser> userMono = appUserRepository.findByUsername(username);
        return userMono.map(appUser -> new UserDetailsImpl(appUser.getUsername(), appUser.getPassword(),
                Optional.ofNullable(appUser.getAuthorities())
                        .map(SimpleGrantedAuthority::new)
                        .map(Collections::singletonList).orElse(Collections.EMPTY_LIST)));

    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/*        AppUser user = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        String authorities = user.getAuthorities();
        String password = user.getPassword();
        List<GrantedAuthority> arrayList = new ArrayList<>();
        for (String permissionCode : authorities.split("\\|")) {
            if (permissionCode != null && !permissionCode.isEmpty()) {
                GrantedAuthority grantedAuthority =
                        new SimpleGrantedAuthority(permissionCode);
                arrayList.add(grantedAuthority);
            }
        }
        log.info(user.toString());
        return new UserDetailsImpl(username, password, arrayList);
        */
        String password="123456";
        return new UserDetailsImpl(username, password, AuthorityUtils.NO_AUTHORITIES);
    }
}
