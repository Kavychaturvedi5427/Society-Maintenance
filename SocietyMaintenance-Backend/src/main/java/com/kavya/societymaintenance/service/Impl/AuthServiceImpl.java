package com.kavya.societymaintenance.service.Impl;

import org.jspecify.annotations.Nullable;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kavya.societymaintenance.dto.Request.LoginRequest;
import com.kavya.societymaintenance.dto.Request.RegisterRequest;
import com.kavya.societymaintenance.dto.Response.AuthResponse;
import com.kavya.societymaintenance.entity.Role;
import com.kavya.societymaintenance.entity.User;
import com.kavya.societymaintenance.repository.UserRepository;
import com.kavya.societymaintenance.service.AuthService;
import com.kavya.societymaintenance.utils.AuthUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // used for final fields..
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setFlatNumber(request.getFlatNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encoding password...

        // assigning default role of resident...
        user.setRole(Role.ROLE_RESIDENT);

        User savedUser = userRepository.save(user);

        String token = authUtils.generateJWTtoken(savedUser);

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .role(savedUser.getRole().name())
                .userId(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .message("Registration Successful")
                .build();

    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        // this will authenticate the user....
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = (User) authentication.getPrincipal();

        String token = authUtils.generateJWTtoken(user);
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .role(user.getRole().name())
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .message("Registration Successful")
                .build();

    }

}
