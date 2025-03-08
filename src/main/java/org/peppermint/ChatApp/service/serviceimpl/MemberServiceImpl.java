package org.peppermint.ChatApp.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.peppermint.ChatApp.dto.MemberDTO;
import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.exception.EntityNotFoundException;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.MemberId;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.repository.MemberRepository;
import org.peppermint.ChatApp.service.ChatRoomService;
import org.peppermint.ChatApp.service.MemberService;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    private final ChatRoomService roomService;
    private final UserService userService;
    @Override
    public Member joinChatRoom(String userId, String roomCode, String role) {
        User user = userService.getUser(userId);
        ChatRoom room = roomService.getChatRoom(roomCode);

        Optional<Member> opMember = memberRepository.findByUserAndRoom(user, room);
        if (opMember.isPresent()) {
            memberRepository.delete(opMember.get());
            return null;
        } else {
            Member member = Member.builder()
                    .username(user.getUsername())
                    .user(user)
                    .room(room)
                    .role(role)
                    .build();

            return memberRepository.save(member);
        }
    }

    @Override
    public Member getMember(String roomCode, String userId) {
        ChatRoom room = roomService.getChatRoom(roomCode);
        User user = userService.getUser(userId);
        return memberRepository.findByUserAndRoom(user, room).orElseThrow(() -> new RuntimeException("The member with user id: " + userId + " and room code " + roomCode + " is not exist in our record"));
    }

    @Override
    public Member getMemberById(MemberId memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public Member updateMemberRole(MemberId moderatorId, MemberId memberId, String role) {
        Member admin = memberRepository.findById(moderatorId).orElseThrow(() -> new RuntimeException());
        if (!admin.getRole().equals("ADMIN")) throw new RuntimeException();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        if (member.getRole().equals("ADMIN")) throw new RuntimeException();

        if (!admin.getRoom().getId().equals(member.getRoom().getId())) throw new RuntimeException();

        member.setRole(role);
        return memberRepository.save(member);
    }

    @Override
    public void deleteMember(MemberId moderatorId, MemberId memberId) {
        Member admin = memberRepository.findById(moderatorId).orElseThrow(() -> new RuntimeException());
        if (!admin.getRole().equals("ADMIN")) throw new RuntimeException();

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException());
        if (member.getRole().equals("ADMIN")) throw new RuntimeException();

        if (!admin.getRoom().getId().equals(member.getRoom().getId())) throw new RuntimeException();

        memberRepository.delete(member);
    }

    @Override
    public List<Member> getMembersFromChatRoom(String roomCode) {
        ChatRoom room = roomService.getChatRoom(roomCode);
        List<Member> members = memberRepository.findMembersByRoom(room);
        return members;
    }
}
