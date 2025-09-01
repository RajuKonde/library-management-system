package com.example.librarymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder; // <-- Add this import
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer; // <-- Add this import

@SpringBootApplication
public class LibrarymanagementApplication extends SpringBootServletInitializer { // <-- Change this line

    public static void main(String[] args) {
        SpringApplication.run(LibrarymanagementApplication.class, args);
    }

    // ADD THIS METHOD

}