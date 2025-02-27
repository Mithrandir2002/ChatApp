package org.peppermint.ChatApp.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.exception.EntityNotFoundException;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.repository.ChatRoomRepository;
import org.peppermint.ChatApp.service.ChatRoomService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    ChatRoomRepository chatRoomRepository;
    UserService userService;

    @Override
    public ChatRoom createChatRoom(ChatRoom room, String userId) {
        User user = userService.getUser(userId);
        ChatRoom chatRoom = ChatRoom.builder()
                .roomCode(generateRoomCode())
                .name(room.getName())
                .createdBy(user)
                .build();
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom getChatRoom(String roomcode) {
        ChatRoom room = chatRoomRepository.findByRoomCode(roomcode).orElseThrow(() -> new EntityNotFoundException(roomcode, ChatRoom.class));
        return room;
    }

    @Override
    public ChatRoom updateChatRoom(ChatRoom room) {
        return null;
    }

    @Override
    public void deleteChatRoom(String id) {

    }

    private String generateRoomCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

}
