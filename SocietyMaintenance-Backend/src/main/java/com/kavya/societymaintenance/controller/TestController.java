package com.kavya.societymaintenance.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kavya.societymaintenance.entity.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping
    public ResponseEntity<String> test(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok("Hello " + user.getName() + "(" + user.getRole() + ")");
    }
    


}
