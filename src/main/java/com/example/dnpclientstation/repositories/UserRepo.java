package com.example.dnpclientstation.repositories;

import com.example.dnpclientstation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findById(Long id);

    User findByEmail(String email);

    User findByActivationCode(String code);
}
