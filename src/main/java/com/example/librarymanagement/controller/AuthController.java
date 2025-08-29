package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        // --- DEBUGGING START ---
        System.out.println("--- NEW REGISTRATION ATTEMPT ---");
        System.out.println("Password received from form: '" + user.getPassword() + "'"); // DEBUG LINE 1

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println("Hashed password to be saved: '" + hashedPassword + "'"); // DEBUG LINE 2
        // --- DEBUGGING END ---

        user.setPassword(hashedPassword);
        user.setRole("ROLE_USER");
        user.setJoinDate(LocalDate.now());

        userRepository.save(user);

        // --- DEBUGGING START ---
        System.out.println("User details saved successfully to the database."); // DEBUG LINE 3
        System.out.println("---------------------------------");
        // --- DEBUGGING END ---

        return "redirect:/login?success";
    }
}