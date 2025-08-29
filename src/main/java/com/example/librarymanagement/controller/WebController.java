package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository; // Required to fetch books

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", currentUser);

        // Check the user's role
        if ("ROLE_USER".equals(currentUser.getRole())) {
            // If they are a user, get their loans
            List<Loan> userLoans = loanRepository.findByUserId(currentUser.getId());
            model.addAttribute("loans", userLoans);

            // --- THIS IS THE CRITICAL PART THAT WAS MISSING ---
            // Also get the list of ALL books for them to browse
            List<Book> allBooks = bookRepository.findAll();
            model.addAttribute("books", allBooks);
            // -------------------------------------------------

        }
        // Admins don't need the loan/book list on this specific page

        return "dashboard";
    }
}