package com.kavya.societymaintenance.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kavya.societymaintenance.entity.User;
import com.kavya.societymaintenance.enumerated.Role;
import com.kavya.societymaintenance.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// file for registering the default admin user...
@Component
@RequiredArgsConstructor
public class DataIntializr implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByEmail("admin@gmail.com")) {

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println("Default admin created.");
        }
    }

}
