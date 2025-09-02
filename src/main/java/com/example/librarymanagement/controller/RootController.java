package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    /**
     * This method handles requests to the root URL ("/") and redirects them
     * to the "/login" page, which is handled by Spring Security.
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }
}