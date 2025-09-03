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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WebController {

    @Autowired private UserRepository userRepository;
    @Autowired private LoanRepository loanRepository;
    @Autowired private BookRepository bookRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", currentUser);

        // This is the fix: Check the user's authority from the security context
        boolean isUserRole = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

        if (isUserRole) {
            List<Loan> userLoans = loanRepository.findByUserId(currentUser.getId());
            model.addAttribute("loans", userLoans);
            List<Book> allBooks = bookRepository.findAll();
            model.addAttribute("books", allBooks);
        }

        return "dashboard";
    }

    @GetMapping("/chat-with-admin")
    public String chatWithAdmin(RedirectAttributes redirectAttributes) {
        List<User> admins = userRepository.findByRole("ROLE_ADMIN");

        if (admins.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No admin is available to chat.");
            return "redirect:/dashboard";
        }
        String adminEmail = admins.get(0).getEmail();
        return "redirect:/chat?recipient=" + adminEmail;
    }
}