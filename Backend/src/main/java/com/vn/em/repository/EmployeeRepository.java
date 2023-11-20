package com.vn.em.repository;

import com.vn.em.domain.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    @Query(value = "SELECT e.* FROM employees e " +
            "INNER JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 = '' OR (LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   ) " +
            "   AND (?2 IS NULL OR p.department_id = ?2) " +
            "   AND (?3 IS NULL OR e.status_id = ?3)", nativeQuery = true)
    Page<Employee> getAll(String keyword, Integer departmentId, Integer statusId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE employees e " +
            "SET e.salary = ?2 " +
            "WHERE e.id = ?1", nativeQuery = true)
    void updateNewSalary(Integer id, Long newSalary);

}
