package xyz.guqing.violet.auth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.auth.model.constant.SocialConstant;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.common.core.model.entity.system.User;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.model.bo.MyUserDetails;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.servlet.http.HttpServletRequest;
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

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }
//    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

//    @Autowired
//    private TokenStore redisTokenStore;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CurrentUser user = userService.loadUserByUsername(username);

        HttpServletRequest httpServletRequest = VioletUtil.getHttpServletRequest();
        String loginType = (String) httpServletRequest.getAttribute(ParamsConstant.LOGIN_TYPE);

        MyUserDetails myUserDetails = new MyUserDetails();
        myUserDetails.setId(user.getId());
        myUserDetails.setUsername(username);
        String password = user.getPassword();
        if(StringUtils.equals(loginType, SocialConstant.SOCIAL_LOGIN)) {
            password = passwordEncoder.encode(SocialConstant.SOCIAL_LOGIN_PASSWORD);
        }
        myUserDetails.setPassword(password);
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_ADMIN");
        myUserDetails.setRoles(roles);

        // 返回自定义的 MyUserDetails
        return myUserDetails;
    }


}