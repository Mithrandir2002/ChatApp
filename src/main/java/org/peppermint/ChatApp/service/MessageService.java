package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.model.Message;

import java.util.List;

public interface MessageService {
    public Message createMessage(String userId, String channelId, Message message);
    public Message getMessage(String userId, String channelId);
    public Message deleteMessage(String userId, String messageId);
    public List<Message> getMessagesFromChannel(String channelId, String userId);
    public List<Message> getMessagesFromChannelFromSpecificUser(String channelId, String userId, String moderatorId);
}
