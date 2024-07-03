package ru.emin.security.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/unsecured")
    public String unsecureddata(){
        return "unsecured data";
    }

    @GetMapping("/secured")
    public String secureddata(){
        return "secured data";
    }

    @GetMapping("/admin")
    public String admindata(){
        return "admin data";
    }

    @GetMapping("/info")
    public String userData(Principal principal){
        return principal.getName();
    }
}
