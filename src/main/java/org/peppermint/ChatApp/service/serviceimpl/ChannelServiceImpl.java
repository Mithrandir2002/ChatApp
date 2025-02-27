package org.peppermint.ChatApp.service.serviceimpl;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.MemberId;
import org.peppermint.ChatApp.repository.ChannelRepository;
import org.peppermint.ChatApp.repository.ChatRoomRepository;
import org.peppermint.ChatApp.service.ChannelService;
import org.peppermint.ChatApp.service.ChatRoomService;
import org.peppermint.ChatApp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChannelServiceImpl implements ChannelService {
    @Autowired
    public final ChannelRepository channelRepository;
    public final ChatRoomService roomService;
    public final MemberService memberService;

    @Override
    public Channel createChannel(Channel channel, String roomCode, String userId) {
        ChatRoom room = roomService.getChatRoom(roomCode);
        Member member = memberService.getMember(roomCode, userId);
        if (member.getRole().equals("MEMBER")) throw new RuntimeException();
        Channel savedChannel = Channel.builder()
                .name(channel.getName())
                .room(room)
                .build();
        return channelRepository.save(savedChannel);
    }

    @Override
    public Channel getChannel(String channelId, String userId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new RuntimeException());
        Member member = memberService.getMember(channel.getRoom().getRoomCode(), userId);
        return channel;
    }

    @Override
    public Channel updateChannel(String id) {
        return null;
    }

    @Override
    public void deleteChannel(String id) {

    }
}
