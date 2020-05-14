package xyz.guqing.violet.auth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.auth.security.support.MyUserDetails;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.entity.system.SystemUser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author guqing
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

//    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

//    @Autowired
//    private TokenStore redisTokenStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username is:" + username);

        SystemUser user = userService.loadUserByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("the user is not found");
        }

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setId(user.getId());
        myUserDetails.setUsername(username);
        myUserDetails.setPassword(user.getPassword());

        Set<String> roles = new HashSet<>();
        roles.add(user.getRoleName());
        myUserDetails.setRoles(roles);

        // 返回自定义的 MyUserDetails
        return myUserDetails;
    }


}