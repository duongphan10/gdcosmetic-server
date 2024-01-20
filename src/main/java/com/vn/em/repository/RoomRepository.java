package com.vn.em.repository;

import com.vn.em.domain.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query(value = "SELECT r.* FROM rooms r " +
            "LEFT JOIN user_rooms ur ON r.id = ur.room_id " +
            "LEFT JOIN messages m ON r.id = m.room_id " +
            "WHERE " +
            "    ur.user_id = ?1 " +
            "GROUP BY r.id " +
            "ORDER BY MAX(COALESCE(m.created_date, r.created_date)) DESC", nativeQuery = true)
    Page<Room> getAllMyConversation(Integer userId, Pageable pageable);

}
