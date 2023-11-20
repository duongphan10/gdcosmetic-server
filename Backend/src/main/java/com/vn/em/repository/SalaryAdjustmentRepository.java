package com.vn.em.repository;

import com.vn.em.domain.entity.SalaryAdjustment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryAdjustmentRepository extends JpaRepository<SalaryAdjustment, Integer> {

    @Query(value = "SELECT s.* FROM salary_adjustments s " +
            "INNER JOIN employees e  ON s.employee_id = e.id " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   ) " +
            "   AND (?2 IS NULL OR p.department_id = ?2) " +
            "   AND (?3 IS NULL OR s.status_id = ?3)", nativeQuery = true)
    Page<SalaryAdjustment> getAll(String keyword, Integer departmentId, Integer statusId, Pageable pageable);

    @Query(value = "SELECT s.* FROM salary_adjustments s " +
            "INNER JOIN employees e ON s.employee_id = e.id " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "    s.created_by = ?1 " +
            "    AND (?2 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?2, '%')) " +
            "                   OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?2, '%')) " +
            "                   OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "    ) " +
            "    AND p.department_id = ?3 " +
            "    AND (?4 IS NULL OR s.status_id = ?4)", nativeQuery = true)
    Page<SalaryAdjustment> getAllMyCreate(Integer userId, String keyword, Integer departmentId, Integer statusId, Pageable pageable);
}
