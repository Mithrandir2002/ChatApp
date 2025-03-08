package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.ChannelDTO;
import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.ChannelService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ChannelController {
    @Autowired
    public ChannelService channelService;
    public UserService userService;

    @PostMapping("/api/room/{roomCode}")
    public ResponseEntity<ChannelDTO> createChannel(@RequestHeader("Authorization") String token, @RequestBody Channel channel, @PathVariable String roomCode) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        return new ResponseEntity<>(mapToChannelDTO(channelService.createChannel(channel, roomCode, user.getId())), HttpStatus.CREATED);
    }

    @GetMapping("/api/channel/{channelId}")
    public ResponseEntity<ChannelDTO> getChannel(@RequestHeader("Authorization") String token, @PathVariable String channelId) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        return new ResponseEntity<>(mapToChannelDTO(channelService.getChannel(channelId)), HttpStatus.OK);
    }
    

    public ChannelDTO mapToChannelDTO(Channel channel) {
        return ChannelDTO.builder()
                .name(channel.getName())
                .build();
    }
}
