package xyz.guqing.violet.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.guqing.violet.auth.manager.UserManager;
import xyz.guqing.violet.common.core.entity.FebsAuthUser;
import xyz.guqing.violet.common.core.entity.constant.ParamsConstant;
import xyz.guqing.violet.common.core.entity.constant.SocialConstant;
import xyz.guqing.violet.common.core.entity.system.SystemUser;
import xyz.guqing.violet.common.core.utils.FebsUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author MrBird
 */
@Service
@RequiredArgsConstructor
public class FebsUserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserManager userManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest httpServletRequest = FebsUtil.getHttpServletRequest();
        SystemUser systemUser = userManager.findByName(username);
        if (systemUser != null) {
            String permissions = userManager.findUserPermissions(systemUser.getUsername());
            boolean notLocked = false;
            if (StringUtils.equals(SystemUser.STATUS_VALID, systemUser.getStatus())) {
                notLocked = true;
            }
            String password = systemUser.getPassword();
            String loginType = (String) httpServletRequest.getAttribute(ParamsConstant.LOGIN_TYPE);
            if (StringUtils.equals(loginType, SocialConstant.SOCIAL_LOGIN)) {
                password = passwordEncoder.encode(SocialConstant.SOCIAL_LOGIN_PASSWORD);
            }
            FebsAuthUser authUser = new FebsAuthUser(systemUser.getUsername(), password, true, true, true, notLocked,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

            BeanUtils.copyProperties(systemUser, authUser);
            return authUser;
        } else {
            throw new UsernameNotFoundException("");
        }
    }
}
