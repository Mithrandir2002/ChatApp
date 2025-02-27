package org.peppermint.ChatApp.service;

import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.MemberDTO;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoomCreationService {
    public final MemberService memberService;
    public final ChatRoomService roomService;

    @Transactional
    public ChatRoom createRoomWithAdmin(ChatRoom room, String userId) {
        ChatRoom chatRoom = roomService.createChatRoom(room, userId);
        Member member = memberService.joinChatRoom(userId, chatRoom.getRoomCode(), "ADMIN");
        return chatRoom;
    }
}
