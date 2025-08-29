package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // <-- Add this import
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Spring Data JPA automatically provides methods like findAll(), findById(), save(), etc.
    // No extra code is needed here for now.
    List<Loan> findByUserId(Long userId);
}