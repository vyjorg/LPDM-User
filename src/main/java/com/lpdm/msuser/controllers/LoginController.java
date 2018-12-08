package com.lpdm.msuser.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/identification")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "identification/login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "identification/registration";
    }
}
