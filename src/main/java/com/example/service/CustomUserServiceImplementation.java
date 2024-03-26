package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class CustomUserServiceImplementation implements UserDetailsService {

    private UserRepository userRepository;
    private  PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email-" + username);
        }

        // Populate authorities if needed
        List<GrantedAuthority> authorities =new ArrayList<>();

        // Return UserDetails with encoded password and authorities
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                authorities
        );
    }
}
