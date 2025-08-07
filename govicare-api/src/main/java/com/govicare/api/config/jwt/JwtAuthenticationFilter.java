package com.govicare.api.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Marks this class as a Spring component, making it eligible for dependency injection.
@RequiredArgsConstructor // Creates a constructor with all final fields, for dependency injection.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extract the "Authorization" header from the incoming request.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check if the header is missing or doesn't start with "Bearer ".
        // If so, it's not a request we need to process for JWT auth, so we pass it on.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token from the header (by removing "Bearer ").
        jwt = authHeader.substring(7);

        // 4. Use the JwtService to parse the token and extract the user's email (the subject).
        userEmail = jwtService.extractUsername(jwt);

        // 5. Check if we have an email and the user is not already authenticated.
        // The second check is important to avoid re-doing this logic for every filter in the chain.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load the user's details from the database via the UserDetailsService.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Validate the token.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // If the token is valid, create an authentication object.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed as we are using a token.
                        userDetails.getAuthorities()
                );
                // Set details of the request (like IP address, session ID) into the auth token.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // 7. Update the SecurityContextHolder. From this point on, the user is authenticated.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 8. Pass the request and response to the next filter in the chain.
        filterChain.doFilter(request, response);
    }
}
