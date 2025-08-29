package com.example.librarymanagement.service;

import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ---- DEBUG LOGGING START ----
        System.out.println("--- LOGIN ATTEMPT ---");
        System.out.println("Attempting to load user by email: " + email);
        // ---- DEBUG LOGGING END ----

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    // ---- DEBUG LOGGING START ----
                    System.out.println("!!! LOGIN FAILED: User not found in database with email: " + email);
                    // ---- DEBUG LOGGING END ----
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        // ---- DEBUG LOGGING START ----
        System.out.println(">>> LOGIN SUCCESS: User found in database!");
        System.out.println("User Email: " + user.getEmail());
        System.out.println("User Hashed Password: " + user.getPassword());
        System.out.println("User Role: " + user.getRole());
        System.out.println("--------------------");
        // ---- DEBUG LOGGING END ----

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}