package org.peppermint.ChatApp.repository;

import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {

    @Query("select m from Message m where m.owner = :owner")
    List<Message> findByOwner(@Param("owner")Member member);

    @Query("select m from Message m where m.channel = :channel")
    List<Message> findByChannel(@Param("channel")Channel channel);
}
