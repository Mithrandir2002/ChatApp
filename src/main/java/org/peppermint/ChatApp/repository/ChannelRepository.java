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
public interface ChannelRepository extends JpaRepository<Channel, String> {
    @Query("select c from Channel c where c.room = :room")
    List<Channel> findByRoom(@Param("room") ChatRoom room);

//    @Query("select c from Channel c where c.name =: name")
//    Optional<Channel> findByName(@Param("name") String name);
}
