package org.peppermint.ChatApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.nio.channels.Channels;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "owner_user_id", referencedColumnName = "user_id"),
            @JoinColumn(name = "owner_room_id", referencedColumnName = "room_id")
    })
    private Member owner;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    private String type;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean isDeleted = false;
}
