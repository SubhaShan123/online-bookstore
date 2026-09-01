package com.bookstore.repository;
import com.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<com.bookstore.model.User> findByUsername(String username);
}
