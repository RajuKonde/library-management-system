package com.example.librarymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        // This redirects any request for the main URL "/"
        // to the "/login" page.
        return "redirect:/login";
    }
}