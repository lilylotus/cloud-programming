package cn.nihility.security.oauth2.sso.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserDetailsController {

    @GetMapping("/user/getCurrentUser")
    @ResponseBody
    public Object user(Authentication authentication) {
        return authentication;
    }

}
