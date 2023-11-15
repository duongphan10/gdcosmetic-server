package com.vn.em.repository;

import com.vn.em.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

}
