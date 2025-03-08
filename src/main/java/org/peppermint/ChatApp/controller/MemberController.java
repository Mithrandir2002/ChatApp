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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
        return new ResponseEntity<>(mapToMemberDTO(member), HttpStatus.CREATED);
    }

    @GetMapping("/api/{roomCode}")
    public ResponseEntity<List<MemberDTO>> getMembersFromChatRoom(@PathVariable String roomCode, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        User user = userService.findUserByToken(token);
        List<Member> members = memberService.getMembersFromChatRoom(roomCode);
        return new ResponseEntity<>(mapToMemberDTOS(members), HttpStatus.OK);
    }

    public List<MemberDTO> mapToMemberDTOS(List<Member> members) {
        List<MemberDTO> memberDTOS = new ArrayList<>();
        members.stream().forEach(member -> memberDTOS.add(mapToMemberDTO(member)));
        return memberDTOS;
    }

    public MemberDTO mapToMemberDTO(Member member) {
        MemberDTO memberDTO = MemberDTO.builder()
                .role(member.getRole())
                .username(member.getUsername())
                .build();
        return memberDTO;
    }
}
