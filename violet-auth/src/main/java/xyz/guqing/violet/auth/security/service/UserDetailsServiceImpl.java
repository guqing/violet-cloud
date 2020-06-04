package xyz.guqing.violet.auth.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.guqing.violet.auth.model.constant.SocialConstant;
import xyz.guqing.violet.auth.service.MenuService;
import xyz.guqing.violet.common.core.model.bo.CurrentUser;
import xyz.guqing.violet.auth.service.UserService;
import xyz.guqing.violet.common.core.model.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.model.bo.MyUserDetails;
import xyz.guqing.violet.common.core.utils.VioletUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author guqing
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MenuService menuService;

    public UserDetailsServiceImpl(PasswordEncoder passwordEncoder,
                                  UserService userService,
                                  MenuService menuService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.menuService = menuService;
    }
//    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

//    @Autowired
//    private TokenStore redisTokenStore;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CurrentUser user = userService.loadUserByUsername(username);

        HttpServletRequest httpServletRequest = VioletUtil.getHttpServletRequest();
        String loginType = (String) httpServletRequest.getAttribute(ParamsConstant.LOGIN_TYPE);

        String password = user.getPassword();
        if(StringUtils.equals(loginType, SocialConstant.SOCIAL_LOGIN)) {
            password = passwordEncoder.encode(SocialConstant.SOCIAL_LOGIN_PASSWORD);
        }
        String permissions = menuService.findUserPermissions(username);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
        if (StringUtils.isNotBlank(permissions)) {
            grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(permissions);
        }

        MyUserDetails myUserDetails = new MyUserDetails(
                username,
                password,
                true,
                true,
                true,
                true,
                grantedAuthorities
        );

        BeanUtils.copyProperties(user, myUserDetails);
        myUserDetails.setRoleIds(VioletUtil.commaSeparatedToList(user.getRoleId()));
        myUserDetails.setRoleNames(VioletUtil.commaSeparatedToList(user.getRoleName()));
        // 返回自定义的 MyUserDetails
        return myUserDetails;
    }


}