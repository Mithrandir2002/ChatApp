package org.peppermint.ChatApp.service.serviceimpl;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.Message;
import org.peppermint.ChatApp.repository.MessageRepository;
import org.peppermint.ChatApp.service.ChannelService;
import org.peppermint.ChatApp.service.MemberService;
import org.peppermint.ChatApp.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    public final MessageRepository messageRepository;
    public final MemberService memberService;
    public final ChannelService channelService;

    @Override
    public Message createMessage(String userId, String channelId, Message message) {
        Channel channel = channelService.getChannel(channelId, userId);
        Member member = memberService.getMember(channel.getRoom().getRoomCode(), userId);

        if ((message.getContent() == null || message.getContent().isEmpty())
                && (message.getFileUrl() == null || message.getFileUrl().isEmpty())) {
            throw new RuntimeException("Message must have content or a file");
        }

        Message savedMsg = Message.builder()
                .owner(member)
                .channel(channel)
                .content(message.getContent())
                .fileUrl(message.getFileUrl())
                .fileName(message.getFileName())
                .fileSize(message.getFileSize())
                .build();
        return messageRepository.save(savedMsg);
    }

    @Override
    public Message getMessage(String userId, String channelId) {
        return null;
    }

    @Override
    public Message deleteMessage(String userId, String messageId) {
        return null;
    }

    @Override
    public List<Message> getMessagesFromChannel(String channelId, String userId) {
        return null;
    }

    @Override
    public List<Message> getMessagesFromChannelFromSpecificUser(String channelId, String userId, String moderatorId) {
        return null;
    }
}
