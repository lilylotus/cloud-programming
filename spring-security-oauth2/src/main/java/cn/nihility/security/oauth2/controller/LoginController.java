package cn.nihility.security.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    /*@RequestMapping("/login")
    public String login() {
        return "redirect:login.html";
    }*/


    @GetMapping("/user/getCurrentUser")
    @ResponseBody
    public Object user(Authentication authentication) {
        return authentication;
    }

}
