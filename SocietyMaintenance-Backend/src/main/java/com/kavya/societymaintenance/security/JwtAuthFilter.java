package com.kavya.societymaintenance.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kavya.societymaintenance.entity.User;
import com.kavya.societymaintenance.utils.AuthUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { // filter runs for every HTTP request....

    private final CustomUserDetailService customUserDetailService;
    private final AuthUtils authUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // if request is auth related then move forward in filter chain...
        String path = request.getServletPath();
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // get the request header...
        final String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            // if it's null or it is not a Bearer token then move forward in filter chain...
            filterChain.doFilter(request, response);
            return;
        }

        // otherwise get the token..
        String token = header.substring(7);
        String username = authUtils.extractUsername(token);

        // everytime the request is made the context.holder is empty so each time the
        // user is fetched from the db and is loaded in the context.holder and once the
        // request is processed the context.holder is destroyed...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = (User) customUserDetailService.loadUserByUsername(username);

            if (authUtils.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
                        user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
