package cn.nihility.cloud.security.service;

import cn.nihility.cloud.security.pojo.DefaultUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class AccessRequestUriPermissionService {

    public boolean check(Authentication authentication, HttpServletRequest request) {

        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();

        System.out.println("请求 [" + requestURI + "] 校验用户 [" + principal + "] 是否可访问。");

        if (principal instanceof DefaultUserDetails) {
            DefaultUserDetails userDetails = (DefaultUserDetails) principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return authorities.contains(new SimpleGrantedAuthority("access"))
                    || authorities.contains(new SimpleGrantedAuthority("ROLE_access"))
                    || authorities.contains(new SimpleGrantedAuthority(requestURI));
        }

        return false;
    }

}
