package com.blaze.map.service;


import com.blaze.map.dto.AuthRequest;
import com.blaze.map.dto.RegisterRequest;
import com.blaze.map.entity.User;
import com.blaze.map.repository.UserRepository;
import com.blaze.map.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest req) {
        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .enabled(true)
                .build();
        userRepository.save(user);
        return jwtService.generateToken(user.getUsername());
    }

    public String authenticate(AuthRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(req.getUsername());
        }
        throw new RuntimeException("Invalid credentials");
    }
}