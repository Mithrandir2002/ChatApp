package org.peppermint.ChatApp.repository;

import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    @Query("select r from ChatRoom r where r.roomCode = :roomCode")
    Optional<ChatRoom> findByRoomCode(@Param("roomCode") String roomCode);

    @Query("select r from ChatRoom r where r.name like %:name%")
    List<ChatRoom> findByRoomName(@Param("name") String name);

    @Query("select r from ChatRoom r join r.channels c where c = :channel")
    Optional<ChatRoom> findByChannel(@Param("channel")Channel channel);
}
