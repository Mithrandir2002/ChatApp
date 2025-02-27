package org.peppermint.ChatApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.channels.Channels;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private String fileUrl;

    @Column(nullable = true)
    private String fileName;

    @Column(nullable = true)
    private Long fileSize;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean isDeleted = false;
}
