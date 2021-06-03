package cn.nihility.security.oauth2.sso.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author intel
 * @date 2021/05/12 10:54
 */
/*@Configuration
@EnableResourceServer*/
public class SecurityOAuth2ResourceConfiguration extends ResourceServerConfigurerAdapter {

    /*@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }*/

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();

        http.requestMatchers().antMatchers("/user/**");

    /*    http.sessionManagement()
                //不创建session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //资源授权规则
                .authorizeRequests().antMatchers("/product/**").hasAuthority("product")
                //所有的请求对应访问的用户都要有all范围的权限
                .antMatchers("/**").access("#oauth2.hasScope('all')");*/

    }

}
