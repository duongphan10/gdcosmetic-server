package com.vn.em.repository;

import com.vn.em.domain.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query(value = "SELECT * FROM notifications n " +
            "WHERE n.user_id = ?1 ", nativeQuery = true)
    Page<Notification> getAllByUserId(Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM notifications n " +
            "WHERE n.user_id = ?1 " +
            "   AND n.seen = 0 ", nativeQuery = true)
    List<Notification> getUnreadByUserId(Integer userId);

    @Modifying
    @Query(value = "UPDATE notifications n " +
            "SET " +
            "   n.seen = 1 " +
            "WHERE " +
            "   n.user_id = ?1 AND n.seen = 0 ", nativeQuery = true)
    void seenAllByUserId(Integer userId);

}
