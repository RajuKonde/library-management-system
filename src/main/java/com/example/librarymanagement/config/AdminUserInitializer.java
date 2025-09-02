package com.example.librarymanagement.config;

import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if an ADMIN user already exists to avoid creating duplicates
        if (userRepository.findByRole("ROLE_ADMIN").isEmpty()) {
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@library.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default admin password
            admin.setRole("ADMIN");
            admin.setJoinDate(LocalDate.now());

            userRepository.save(admin);
            System.out.println(">>>>>>>>>> Created default ADMIN user <<<<<<<<<<");
        }
    }
}