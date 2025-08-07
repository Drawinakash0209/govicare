package com.govicare.api.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.govicare.api.auth.dto.AuthenticationRequest;
import com.govicare.api.auth.dto.AuthenticationResponse;
import com.govicare.api.auth.dto.RegisterRequest;
import com.govicare.api.config.jwt.JwtService;
import com.govicare.api.user.Role;
import com.govicare.api.user.User;
import com.govicare.api.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	
	//Dependencies injected via constructor
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	//Register a new user
	// @Param RegisterRequest request
	// @Return AuthenticationResponse with JWT token
	public AuthenticationResponse register(RegisterRequest request) {
		
		var user = User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.location(request.getLocation())
				.role(Role.USER)
				.build();
		
		userRepository.save(user);
		
		var JwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(JwtToken)
				.build();
	}
	
	
	public AuthenticationResponse authenitcate(AuthenticationRequest request) {
		
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
			);
		
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
		
	}


}
