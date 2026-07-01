package com.kavya.societymaintenance.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrfconfig -> csrfconfig.disable())
                .sessionManagement(
                        sessionconfig -> sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**") // permit all the auth request...
                        .permitAll()
                        // .anyRequest().authenticated()) // other than that all request are
                        // authenticated... any authenticates user can access all endpoints...
                        .requestMatchers(HttpMethod.POST, "/api/complaints/").hasRole("RESIDENT") // if the user is
                                                                                                  // authenticated and
                                                                                                  // has the role of
                                                                                                  // resident then only
                                                                                                  // these endpoints are
                                                                                                  // accessible...
                        .requestMatchers(HttpMethod.GET, "/api/complaints/my").hasRole("RESIDENT")

                        .requestMatchers("/api/complaints/**")
                        .hasRole("RESIDENT")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // run jwt filter before
                                                                                             // the usernamepassword
                                                                                             // filter...

        return http.build();

    }

}
