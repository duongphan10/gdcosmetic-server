package com.vn.em.repository;

import com.vn.em.domain.entity.UserRoom;
import com.vn.em.domain.entity.UserRoomKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, UserRoomKey> {

    @Query(value = "SELECT ur.* " +
            "FROM user_rooms ur " +
            "WHERE " +
            "   ur.room_id = ?1", nativeQuery = true)
    Page<UserRoom> getAllUserByRoomId(Integer roomId, Pageable pageable);

    @Query(value = "SELECT ur.* " +
            "FROM user_rooms ur " +
            "WHERE " +
            "   ur.room_id = ?1", nativeQuery = true)
    List<UserRoom> getAllUserByRoomId(Integer roomId);

    boolean existsById(UserRoomKey userRoomKey);
}
