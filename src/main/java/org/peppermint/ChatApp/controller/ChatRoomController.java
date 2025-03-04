package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.RoomDTO;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.ChatRoomService;
import org.peppermint.ChatApp.service.MemberService;
import org.peppermint.ChatApp.service.RoomCreationService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class ChatRoomController {
    public final ChatRoomService chatRoomService;
    public final RoomCreationService roomCreationService;
    public final UserService userService;

    @PostMapping("/api/room")
    public ResponseEntity<RoomDTO> createChatRoom(@RequestHeader("Authorization") String token, @RequestBody ChatRoom room) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        ChatRoom chatRoom = roomCreationService.createRoomWithAdmin(room, user.getId());
        return new ResponseEntity<>(mapToRoomDTO(chatRoom), HttpStatus.CREATED);
    }

    public RoomDTO mapToRoomDTO(ChatRoom room) {
        RoomDTO roomDTO = RoomDTO.builder()
                .name(room.getName())
                .roomCode(room.getRoomCode())
                .createAt(room.getCreatedAt())
                .createdBy(room.getCreatedBy().getUsername())
                .build();
        return roomDTO;
    }
}
