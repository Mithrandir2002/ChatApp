package org.peppermint.ChatApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(MemberId.class)
public class Member {
    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String username;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @CreationTimestamp
    private LocalDateTime joinedAt;
}
