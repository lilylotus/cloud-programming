package cn.nihility.cloud.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author intel
 * @date 2021/05/08 16:36
 */
public class PasswordTest {

    @Test
    public void testPasswordEncrypt() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("test")
                .password("test")
                .roles("users")
                .build();
        System.out.println(userDetails.getPassword());
    }

    @Test
    public void testBCrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("myPassword");
        Assertions.assertTrue(encoder.matches("myPassword", result));
    }

    @Test
    public void test() {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        String result = encoder.encode("myPassword");
        Assertions.assertTrue(encoder.matches("myPassword", result));
    }

}
