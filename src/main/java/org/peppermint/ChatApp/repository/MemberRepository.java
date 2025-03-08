package org.peppermint.ChatApp.repository;

import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.MemberId;
import org.peppermint.ChatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
    @Query("select m from Member m where m.user = :user and m.room = :room")
    Optional<Member> findByUserAndRoom(@Param("user") User user, @Param("room") ChatRoom room);

    @Query("select m from Member m where m.user = :user")
    List<Member> findMembersByUser(@Param("user") User user);

    @Query("select m from Member m where m.room = :room")
    List<Member> findMembersByRoom(@Param("room") ChatRoom chatRoom);

    @Query("delete from Member m where m.user = :user")
    void deleteByUser(@Param("user") User user);
}
