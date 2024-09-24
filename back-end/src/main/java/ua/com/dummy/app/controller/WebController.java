package ua.com.dummy.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/users")
    public String users() {
        return "users";
    }

    public String login() {
        return "login";
    }

}