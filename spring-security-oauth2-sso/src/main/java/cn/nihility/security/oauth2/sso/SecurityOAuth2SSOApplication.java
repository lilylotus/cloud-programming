package cn.nihility.security.oauth2.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

/**
 * @author intel
 * @date 2021/05/12 14:15
 */
@SpringBootApplication
@EnableOAuth2Sso
public class SecurityOAuth2SSOApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityOAuth2SSOApplication.class, args);
    }

}
