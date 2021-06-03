package cn.nihility.cloud.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityLoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/toSuccess")
    public String toSuccess() {
        System.out.println("toSuccess");
        return "redirect:success.html";
    }

    @PostMapping("/toFailure")
    public String toFailure() {
        System.out.println("toFailure");
        return "redirect:failure.html";
    }

}
