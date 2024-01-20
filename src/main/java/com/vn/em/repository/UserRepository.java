package com.vn.em.repository;

import com.vn.em.domain.entity.Employee;
import com.vn.em.domain.entity.Role;
import com.vn.em.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmployee(Employee employee);

    @Query("SELECT u FROM User u WHERE u.id = ?1")
    Optional<User> findById(Integer id);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query(value = "SELECT u.* FROM users u " +
            "INNER JOIN employees e ON u.employee_id = e.id " +
            "WHERE e.email = ?1", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    List<User> getAllByRole(Role role);

    @Query(value = "SELECT u.* FROM users u " +
            "LEFT JOIN employees e ON u.employee_id = e.id " +
            "LEFT JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 IS NULL OR p.department_id = ?1) " +
            "   AND (?2 IS NULL OR u.enabled = ?2) " +
            "ORDER BY u.created_date DESC ", nativeQuery = true)
    List<User> getAll(Integer departmentId, Boolean enabled);

    @Query(value = "SELECT u.* FROM users u " +
            "LEFT JOIN employees e ON u.employee_id = e.id " +
            "LEFT JOIN positions p ON e.position_id = p.id " +
            "WHERE " +
            "   (?1 = '' OR (LOWER(u.username) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(e.employee_code) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%')) " +
            "               OR LOWER(p.name) LIKE LOWER(CONCAT('%', ?1, '%'))) " +
            "   )" +
            "   AND (?2 IS NULL OR p.department_id = ?2) " +
            "   AND (?3 IS NULL OR u.enabled = ?3)", nativeQuery = true)
    Page<User> search(String keyword, Integer departmentId, Boolean enabled, Pageable pageable);

    @Query(value = "SELECT u.* FROM users u " +
            "LEFT JOIN employees e ON u.employee_id = e.id " +
            "WHERE " +
            "   u.id NOT IN (" +
            "       SELECT u.id FROM users u " +
            "       INNER JOIN user_rooms ur ON u.id = ur.user_id " +
            "        WHERE " +
            "            ur.room_id = ?2" +
            "   ) " +
            "   AND u.enabled = 1 " +
            "   AND (?1 = '' OR LOWER(e.full_name) LIKE LOWER(CONCAT('%', ?1, '%'))) ", nativeQuery = true)
    Page<User> searchOtherUser(String keyword, Integer roomId, Pageable pageable);

    @Modifying
    @Query(value = "UPDATE users u " +
            "SET u.enabled = 0 " +
            "WHERE u.employee_id = ?1", nativeQuery = true)
    void disabledByEmployee(Integer employeeId);

}
