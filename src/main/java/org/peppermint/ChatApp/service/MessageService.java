package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    public Message createMessage(String userId, String channelId, Message message);
    public Message deleteMessage(String userId, String messageId);
    public List<Message> getMessagesFromChannel(String channelId, String userId, Pageable pageable);
}
