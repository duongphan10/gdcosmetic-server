package com.vn.em.repository;

import com.vn.em.domain.entity.Recognition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecognitionRepository extends JpaRepository<Recognition, Integer> {

    @Query(value = "SELECT r.* FROM recognitions r " +
            "INNER JOIN employees e  ON r.employee_id = e.id " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   ) " +
            "   AND (?2 IS NULL OR p.department_id = ?2) " +
            "   AND (?3 IS NULL OR r.status_id = ?3)", nativeQuery = true)
    Page<Recognition> getAll(String keyword, Integer departmentId, Integer statusId, Pageable pageable);

    @Query(value = "SELECT r.* FROM recognitions r " +
            "INNER JOIN employees e ON r.employee_id = e.id " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   r.created_by = ?1 " +
            "   AND (?2 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?2, '%')) " +
            "                   OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?2, '%')) " +
            "                   OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "   ) " +
            "   AND (?3 IS NULL OR p.department_id = ?3) " +
            "   AND (?4 IS NULL OR r.status_id = ?4)", nativeQuery = true)
    Page<Recognition> getAllMyCreate(Integer userId, String keyword, Integer departmentId, Integer statusId, Pageable pageable);

    @Query(value = "SELECT IFNULL(SUM(IFNULL(r.amount, 0)), 0) AS total_sum " +
            "FROM recognitions r " +
            "WHERE " +
            "   r.type = 1 AND r.status_id = 7 " +
            "   AND r.employee_id = ?1 AND YEAR(r.date) = ?2 AND MONTH(r.date) = ?3", nativeQuery = true)
    long getSumBonus(Integer employeeId, Integer year, Integer month);

    @Query(value = "SELECT IFNULL(SUM(IFNULL(r.amount, 0)), 0) AS total_sum " +
            "FROM recognitions r " +
            "WHERE " +
            "   r.type = 0 AND r.status_id = 7 " +
            "   AND r.employee_id = ?1 AND YEAR(r.date) = ?2 AND MONTH(r.date) = ?3", nativeQuery = true)
    long getSumDeduction(Integer employeeId, Integer year, Integer month);

}
