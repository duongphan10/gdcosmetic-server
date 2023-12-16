package com.vn.em.repository;

import com.vn.em.domain.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
    @Query(value = "SELECT * FROM positions " +
            "WHERE ?1 = 0 OR department_id = ?1", nativeQuery = true)
    List<Position> getAllByDepartmentId(Integer departmentId);
}
