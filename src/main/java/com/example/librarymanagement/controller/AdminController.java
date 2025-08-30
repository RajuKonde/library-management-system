package com.example.librarymanagement.controller;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.repository.BookRepository;
import com.example.librarymanagement.repository.LoanRepository;
import com.example.librarymanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Secures all methods in this class for Admins only
public class AdminController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the main book management page with a list of all books
     * and a form to add a new book.
     */
    @GetMapping("/books")
    public String showBookManagementPage(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("newBook", new Book());
        return "admin-books";
    }

    /**
     * Handles the submission of the "Add New Book" form.
     */
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book newBook) {
        bookRepository.save(newBook);
        return "redirect:/admin/books";
    }

    /**
     * Deletes a book. This is the corrected version.
     * It first deletes all loan records associated with the book to prevent database errors.
     */
    @PostMapping("/books/delete/{id}")
    @Transactional
    public String deleteBook(@PathVariable Long id) {
        // First, delete all loan records for this book.
        loanRepository.deleteByBookId(id);
        // Now it is safe to delete the book itself.
        bookRepository.deleteById(id);
        return "redirect:/admin/books";
    }

    /**
     * Displays the form to edit an existing book, pre-filled with its current details.
     */
    @GetMapping("/books/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "edit-book";
    }

    /**
     * Handles the submission of the "Update Book" form.
     */
    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable("id") long id, @ModelAttribute Book book) {
        // JpaRepository's save method handles both creating new records and updating existing ones.
        bookRepository.save(book);
        return "redirect:/admin/books";
    }

    /**
     * Displays a page with a list of all loans (both active and returned) in the system.
     */
    @GetMapping("/loans")
    public String showAllLoans(Model model) {
        model.addAttribute("loans", loanRepository.findAll());
        return "admin-loans";
    }

    /**
     * Displays a page with a list of all registered users, allowing the admin
     * to start private chats.
     */
    @GetMapping("/users")
    public String showUserManagementPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin-users";
    }
}
