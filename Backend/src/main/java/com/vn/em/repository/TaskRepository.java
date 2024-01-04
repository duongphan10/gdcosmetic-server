package com.vn.em.repository;

import com.vn.em.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   (?1 IS NULL OR t.project_id = ?1) " +
            "   AND (?2 IS NULL OR t.status_id = ?2) " +
            "ORDER BY t.created_date DESC ", nativeQuery = true)
    List<Task> getAll(Integer projectId, Integer statusId);

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   t.created_by = ?1" +
            "   AND (?2 IS NULL OR t.project_id = ?2) " +
            "   AND (?3 IS NULL OR t.status_id = ?3) " +
            "ORDER BY t.created_date DESC ", nativeQuery = true)
    List<Task> getAllMyCreated(Integer userId, Integer projectId, Integer statusId);

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   t.employee_id = ?1 " +
            "   AND (?2 IS NULL OR t.status_id = ?2) " +
            "ORDER BY t.created_date DESC ", nativeQuery = true)
    List<Task> getByEmployeeId(Integer employeeId, Integer statusId);

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   t.employee_id = ?1 " +
            "   AND (?2 = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "   AND (?3 IS NULL OR t.status_id = ?3) ", nativeQuery = true)
    Page<Task> searchByEmployeeId(Integer employeeId, String keyword, Integer statusId, Pageable pageable);

}
