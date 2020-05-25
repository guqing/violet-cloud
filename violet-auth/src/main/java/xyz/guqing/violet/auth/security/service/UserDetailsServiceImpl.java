package xyz.guqing.violet.auth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.common.core.model.bo.MyUserDetails;

import java.util.HashSet;
import java.util.Set;

/**
 * @author guqing
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;

//    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

//    @Autowired
//    private TokenStore redisTokenStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username is:" + username);

//        User user = userService.loadUserByUsername(username);
        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setId(1L);
        myUserDetails.setUsername(username);
        myUserDetails.setPassword(passwordEncoder.encode("123456"));

        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        myUserDetails.setRoles(roles);

        // 返回自定义的 MyUserDetails
        return myUserDetails;
    }


}