package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Loan;
import com.example.librarymanagement.model.User;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;
import com.example.librarymanagement.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired private LoanService loanService;
    @Autowired private LoanRepository loanRepository;
    @Autowired private UserRepository userRepository; // Add this

    @PostMapping("/issue")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Loan> issueBook(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId) {
        return ResponseEntity.ok(loanService.issueBook(bookId, userId));
    }

    @PostMapping("/return/{loanId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Loan> returnBook(@PathVariable("loanId") Long loanId) {
        return ResponseEntity.ok(loanService.returnBook(loanId));
    }

    // Admin can see all loans
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // ---- NEW ENDPOINTS ----

    // A regular user can see their own loan history
    @GetMapping("/my-history")
    public ResponseEntity<List<Loan>> getMyHistory(@AuthenticationPrincipal UserDetails userDetails) {
        // Find the user by their email (which is the username)
        User currentUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Use the new repository method to get their loans
        List<Loan> myLoans = loanRepository.findByUserId(currentUser.getId());
        return ResponseEntity.ok(myLoans);
    }

    // An admin can see the loan history of any user
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        List<Loan> userLoans = loanRepository.findByUserId(userId);
        return ResponseEntity.ok(userLoans);
    }
}