package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    // --- THIS IS THE NEW METHOD ---
    // This will find all users who have a specific role (e.g., "ROLE_ADMIN")
    List<User> findByRole(String role);
}