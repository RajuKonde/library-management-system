package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Secures all methods in this class for Admins only
public class AdminController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/books")
    public String showBookManagementPage(Model model) {
        // Get all books to display in the table
        model.addAttribute("books", bookRepository.findAll());
        // Provide an empty book object for the "Add New Book" form
        model.addAttribute("newBook", new Book());
        return "admin-books"; // Returns the admin-books.html template
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book newBook) {
        bookRepository.save(newBook);
        return "redirect:/admin/books"; // Redirect back to the management page
    }

    @PostMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/admin/books";
    }

    @GetMapping("/loans")
    public String showAllLoans(Model model) {
        model.addAttribute("loans", loanRepository.findAll());
        return "admin-loans"; // Returns the admin-loans.html template
    }
}