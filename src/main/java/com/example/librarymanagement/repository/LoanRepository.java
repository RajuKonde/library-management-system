package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);

    // This new method is needed to fix the delete error.
    // It tells Spring Data JPA to delete all loans that are linked to a specific book ID.
    void deleteByBookId(Long bookId);
}
