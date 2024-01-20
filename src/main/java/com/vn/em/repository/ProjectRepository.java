package com.vn.em.repository;

import com.vn.em.domain.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query(value = "SELECT p.* FROM projects p " +
            "LEFT JOIN employees e ON p.project_manager_id = e.id " +
            "LEFT JOIN positions po ON e.position_id = po.id " +
            "WHERE " +
            "   (?1 IS NULL OR po.department_id = ?1) " +
            "   AND (?2 IS NULL OR p.status_id = ?2) " +
            "ORDER BY p.created_date DESC ", nativeQuery = true)
    List<Project> getAll(Integer departmentId, Integer statusId);

    @Query(value = "SELECT p.* FROM projects p " +
            "WHERE " +
            "   (?1 = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   AND (?2 IS NULL OR p.status_id = ?2) ", nativeQuery = true)
    Page<Project> search(String keyword, Integer statusId, Pageable pageable);

    @Query(value = "SELECT p.* FROM projects p " +
            "WHERE " +
            "   p.project_manager_id = ?1 " +
            "   AND (?2 IS NULL OR p.status_id = ?2) " +
            "ORDER BY p.created_date DESC ", nativeQuery = true)
    List<Project> getAllByEmployeeId(Integer employeeId, Integer statusId);

    @Query(value = "SELECT p.* FROM projects p " +
            "LEFT JOIN tasks t ON p.id = t.project_id " +
            "WHERE " +
            "   (p.project_manager_id = ?1 OR t.employee_id = ?1) " +
            "   AND (?2 = '' OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "   AND (?3 IS NULL OR p.status_id = ?3) ", nativeQuery = true)
    Page<Project> searchByEmployeeId(Integer employeeId, String keyword, Integer statusId, Pageable pageable);

}
