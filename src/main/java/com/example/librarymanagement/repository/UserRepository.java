package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    // Ensure this method is present
    List<User> findByRole(String role);
}