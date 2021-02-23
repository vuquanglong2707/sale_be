package com.saleor.saleor_api.repo;

import com.saleor.saleor_api.table.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoUser extends JpaRepository<User, Long> {
    List<User> findAll();
    List<User> findAllBy(Pageable pageable);
    Optional<User> findById(Long userId);
    Optional<User> findByUsername(String username);
    List<User> findByTitleContaining(String title);
    Boolean existsByUsername(String username);
    List<User> findByUsernameContainingOrEmailContaining(String username, String email);
}