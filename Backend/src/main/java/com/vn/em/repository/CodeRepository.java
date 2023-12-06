package com.vn.em.repository;

import com.vn.em.domain.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, Integer> {
    @Query(value = "SELECT * FROM codes WHERE user_id = ?1", nativeQuery = true)
    Code findByUserId(Integer userId);

}
