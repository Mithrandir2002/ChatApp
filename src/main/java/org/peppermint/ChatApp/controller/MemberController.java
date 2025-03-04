package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.MemberDTO;
import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.MemberService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MemberController {
    @Autowired
    public MemberService memberService;
    public UserService userService;
    @PostMapping("/api/{roomCode}")
    public ResponseEntity<?> joinChatRoom(@PathVariable String roomCode, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        Member member = memberService.joinChatRoom(user.getId(), roomCode, "MEMBER");
        if (member == null) return ResponseEntity.ok("User left the chat room successfully.");
        return new ResponseEntity<>(mapToMemberDTO(member, member.getUser()), HttpStatus.CREATED);
    }

    public MemberDTO mapToMemberDTO(Member member, User user) {
        MemberDTO memberDTO = MemberDTO.builder()
                .role(member.getRole())
                .userDto(UserDTO.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .build();
        return memberDTO;
    }
}
