package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.ChannelDTO;
import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ChannelController {
    @Autowired
    public ChannelService channelService;

    @PostMapping("/api/room/{roomCode}/{userId}")
    public ResponseEntity<ChannelDTO> createChannel(@RequestBody Channel channel, @PathVariable String roomCode, @PathVariable String userId) {
        return new ResponseEntity<>(mapToChannelDTO(channelService.createChannel(channel, roomCode, userId)), HttpStatus.CREATED);
    }

    public ChannelDTO mapToChannelDTO(Channel channel) {
        return ChannelDTO.builder()
                .name(channel.getName())
                .build();
    }
}
