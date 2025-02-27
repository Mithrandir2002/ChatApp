package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.service.ChatRoomService;
import org.peppermint.ChatApp.service.MemberService;
import org.peppermint.ChatApp.service.RoomCreationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ChatRoomController {
    public final ChatRoomService chatRoomService;
    public final RoomCreationService roomCreationService;

    @PostMapping("/api/room/{userId}")
    public ResponseEntity<ChatRoom> createChatRoom(@PathVariable("userId") String userId, @RequestBody ChatRoom room) {
        ChatRoom chatRoom = roomCreationService.createRoomWithAdmin(room, userId);
        return new ResponseEntity<>(chatRoom, HttpStatus.CREATED);
    }
}
