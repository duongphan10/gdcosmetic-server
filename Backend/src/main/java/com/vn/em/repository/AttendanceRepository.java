package com.vn.em.repository;

import com.vn.em.domain.entity.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    boolean existsByYearAndMonth(Integer year, Integer month);

    @Query(value = "SELECT a.* FROM attendances a " +
            "INNER JOIN employees e ON a.employee_id = e.id " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "                   OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "                   OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   ) " +
            "   AND (?2 IS NULL OR a.year = ?2) " +
            "   AND (?3 IS NULL OR a.month = ?3) " +
            "   AND (?4 IS NULL OR p.department_id = ?4)", nativeQuery = true)
    Page<Attendance> getAll(String keyword, Integer year, Integer month, Integer departmentId, Pageable pageable);

}
