package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.MessageDTO;
import org.peppermint.ChatApp.model.Message;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.MessageService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class MessageController {
    @Autowired
    public final MessageService messageService;
    public final UserService userService;

    @MessageMapping("/room/{channelId}")
    @SendTo("/topic/{channelId}")
    public MessageDTO sendMessage(Message message,
                               @DestinationVariable String channelId,
                               SimpMessageHeaderAccessor headerAccessor) {
        Map<String, Object> attributes = headerAccessor.getSessionAttributes();
        String username = (String) attributes.get("username");
        String userId = userService.findUserByUsername(username).getId();
        Message savedMsg = messageService.createMessage(userId, channelId, message);
        return mapToMessageDTO(savedMsg);
    }

    @GetMapping("/api/room/{channelId}")
    public ResponseEntity<List<MessageDTO>> findMessagesByChannel(@PathVariable String channelId,
                                                                  @RequestHeader("Authorization") String token,
                                                                  @RequestParam(value = "offset") Integer offset,
                                                                  @RequestParam(value = "pageSize") Integer pageSize,
                                                                  @RequestParam(value = "sortBy", required = false) String sortBy) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        List<Message> messages = messageService.getMessagesFromChannel(channelId, user.getId(), PageRequest.of(offset, pageSize, Sort.by("createdAt")));
        return new ResponseEntity<>(mapToMessagesDTOS(messages), HttpStatus.OK);
    }

    public List<MessageDTO> mapToMessagesDTOS(List<Message> messages) {
        List<MessageDTO> messageDTOS = new ArrayList<>();
        messages.stream().forEach(message -> messageDTOS.add(mapToMessageDTO(message)));
        return messageDTOS;
    }

    public MessageDTO mapToMessageDTO(Message message) {
        MessageDTO msgDTO = MessageDTO.builder()
                .id(message.getId())
                .owner(message.getOwner().getUsername())
                .content(message.getContent())
                .build();
        return msgDTO;
    }
}
