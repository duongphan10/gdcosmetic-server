package com.vn.em.repository;

import com.vn.em.domain.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   t.project_id = ?1 " +
            "   AND (?2 = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "   AND (?3 IS NULL OR t.status_id = ?3) ", nativeQuery = true)
    Page<Task> getAllByProjectId(Integer projectId, String keyword, Integer statusId, Pageable pageable);

    @Query(value = "SELECT t.* FROM tasks t " +
            "WHERE " +
            "   t.employee_id = ?1 " +
            "   AND (?2 = '' OR LOWER(t.name) LIKE LOWER(CONCAT('%', ?2, '%'))) " +
            "   AND (?3 IS NULL OR t.status_id = ?3) ", nativeQuery = true)
    Page<Task> getAllByEmployeeId(Integer employeeId, String keyword, Integer statusId, Pageable pageable);
}
