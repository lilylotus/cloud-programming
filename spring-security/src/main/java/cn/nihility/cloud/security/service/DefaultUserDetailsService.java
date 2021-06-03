package cn.nihility.cloud.security.service;

import cn.nihility.cloud.security.pojo.DefaultUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(DefaultUserDetailsService.class);

    private PasswordEncoder passwordEncoder;

    public DefaultUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("自定义登录逻辑，用户 [" + username + "]");
        if (null == username || "".equals(username)) {
            log.error("userName is null");
            throw new UsernameNotFoundException("username is empty");
        }

        if ("admin".equals(username)) {
            return new DefaultUserDetails("admin", passwordEncoder.encode("123456"),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_admin,access"));
        } else if ("normal".equals(username)) {
            return new DefaultUserDetails("normal", passwordEncoder.encode("123456"),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("normal,ROLE_normal,ROLE_access"));
        } else if ("other".equals(username)) {
            return new DefaultUserDetails("other", passwordEncoder.encode("123456"),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("other,ROLE_other,ROLE_access"));
        } else if ("anonymous".equals(username)) {
            return new DefaultUserDetails("anonymous", passwordEncoder.encode("123456"),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("anonymous,access"));
        } else if ("access".equals(username)) {
            return new DefaultUserDetails("access", passwordEncoder.encode("123456"),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("/access.html"));
        } else {
            log.error("cannot find username [{}] user info", username);
            throw new UsernameNotFoundException("user not exists");
        }

    }

}
