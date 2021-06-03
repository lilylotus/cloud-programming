package cn.nihility.security.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class SecurityOAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManagerBean;
    private UserDetailsService defaultUserDetailsService;
    private AccessTokenConverter myJwtAccessTokenConverter;

    public SecurityOAuth2ServerConfiguration(PasswordEncoder passwordEncoder,
                                             AuthenticationManager authenticationManagerBean,
                                             UserDetailsService defaultUserDetailsService,
                                             AccessTokenConverter myJwtAccessTokenConverter) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManagerBean = authenticationManagerBean;
        this.defaultUserDetailsService = defaultUserDetailsService;
        this.myJwtAccessTokenConverter = myJwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.userDetailsService(defaultUserDetailsService)
                .authenticationManager(authenticationManagerBean)
                .accessTokenConverter(myJwtAccessTokenConverter)
                .tokenStore(new InMemoryTokenStore());
    }

    /**
     * http://10.0.41.80:30050/oauth/authorize?client_id=oauth_client&response_type=code&scop=all@redirect_uri=http://www.baidu.com
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("oauth_client")
                .secret(passwordEncoder.encode("123456"))
                .redirectUris("http://www.baidu.com")
                //.redirectUris("http://10.0.41.80:30060/login")
                .scopes("all")
                .autoApprove(false)
                .accessTokenValiditySeconds(36000)
                .refreshTokenValiditySeconds(36000)
                .authorizedGrantTypes("authorization_code", "password", "refresh_token");

    }

}
