package com.vn.em.repository;

import com.vn.em.domain.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    @Query(value = "SELECT * FROM messages WHERE room_id = ?1", nativeQuery = true)
    Page<Message> getAllByRoomId(Integer roomId, Pageable pageable);

}
