package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;

import java.util.List;

public interface ChatRoomService {
    ChatRoom createChatRoom(ChatRoom room, String userId);
    ChatRoom getChatRoom(String roomcode);
    ChatRoom updateChatRoom(ChatRoom room);
    void deleteChatRoom(String id);
}
