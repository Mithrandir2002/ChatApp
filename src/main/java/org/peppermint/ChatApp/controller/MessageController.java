package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.model.Message;
import org.peppermint.ChatApp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MessageController {
    @Autowired
    public final MessageService messageService;

    @MessageMapping("/room/{channelId}")
    @SendTo("/topic/{channelId}")
    public Message sendMessage(Message message, @DestinationVariable String channelId, String userId) {
        Message savedMsg = messageService.createMessage(userId, channelId, message);
        return savedMsg;
    }
}
