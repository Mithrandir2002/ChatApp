package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.model.Channel;
import org.peppermint.ChatApp.model.MemberId;

public interface ChannelService {
    public Channel createChannel(Channel channel, String roomCode, String memberId);
    public Channel getChannel(String channelId);
    public Channel updateChannel(String id);
    public void deleteChannel(String id);
}
