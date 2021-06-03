package cn.nihility.security.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/**
 * @author intel
 * @date 2021/05/14 13:04
 */
@Configuration
public class JwtTokenConfiguration {

    @Bean
    public AccessTokenConverter myJwtAccessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        tokenConverter.setSigningKey("jwtTokenKey");
        return tokenConverter;
    }

    /*@Bean
    public TokenEnhancer myTokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
                additionalInformation.put("TokenEnhancer", "TokenEnhancer Info");
                return accessToken;
            }
        };
    }*/

}
