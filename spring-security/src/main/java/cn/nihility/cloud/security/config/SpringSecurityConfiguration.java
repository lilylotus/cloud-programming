package cn.nihility.cloud.security.config;

import cn.nihility.cloud.security.handler.MyAccessDeniedHandler;
import cn.nihility.cloud.security.handler.MyAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                // 自定义登录页面
                .loginPage("/login")
                // 自定义登录逻辑
                .loginProcessingUrl("/login")
                // 登录成功后跳转界面，必须是 POST 方式
                .successForwardUrl("/toSuccess")
                // 登录失败后跳转界面，必须是 POST 方式
                //.failureForwardUrl("/toFailure")
                .failureHandler(new MyAuthenticationFailureHandler())
                // 自定义登录用户名参数
                .usernameParameter("username")
                // 自定义密码登录参数
                .passwordParameter("password");

        // 授权
        http.authorizeRequests()
                // 放行登录页面
                .antMatchers("/login.html", "/favicon.ico", "/login", "/success.html", "/failure.html").permitAll()
                .antMatchers("/normal.html", "/admin.html", "/other.html").hasAuthority("admin")
                .antMatchers("/normal.html").hasAuthority("normal")
                .antMatchers("/other.html").hasAuthority("other")
                .antMatchers("/anonymous.html").hasAnyAuthority("anonymous", "normal", "other")
                .antMatchers("/role_admin.html").hasRole("admin")
                //.anyRequest().access("@accessRequestUriPermissionService.check(authentication,request)")
                .anyRequest().authenticated();
                //.antMatchers("/toSuccess", "/toFailure").permitAll();

        http.exceptionHandling().accessDeniedHandler(new MyAccessDeniedHandler());

        http.csrf().disable();

    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
