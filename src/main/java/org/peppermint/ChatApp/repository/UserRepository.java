package org.peppermint.ChatApp.repository;

import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.email = :email")
    public Optional<User> findUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.username = :username")
    public Optional<User> findUserByUsername(@Param("username") String username);
}
