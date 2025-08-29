package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.UserRepository;
import com.example.librarymanagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserActionController {

    @Autowired
    private LoanService loanService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/issue-book/{bookId}")
    public String issueBook(@PathVariable Long bookId, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        loanService.issueBook(bookId, currentUser.getId());
        return "redirect:/dashboard";
    }

    @PostMapping("/return-loan/{loanId}")
    public String returnBook(@PathVariable Long loanId) {
        loanService.returnBook(loanId);
        return "redirect:/dashboard";
    }
}