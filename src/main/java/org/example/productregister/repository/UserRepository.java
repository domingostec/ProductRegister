package org.example.productregister.repository;

import org.example.productregister.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User>findByNameAndEmailAndNumberPhone(String name, String email, String numberPhone);
    boolean existsByEmail(String email);
}
