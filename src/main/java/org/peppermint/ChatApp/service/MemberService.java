package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.dto.MemberDTO;
import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.MemberId;
import org.peppermint.ChatApp.model.User;

import java.util.List;

public interface MemberService {
    Member joinChatRoom(String userId, String roomCode, String role);
    Member getMember(String roomCode, String userid);
    Member getMemberById(MemberId memberId);
    Member updateMemberRole(MemberId moderatorId, MemberId memberId, String role);
    void deleteMember(MemberId moderatorId, MemberId memberId);
    List<Member> getMembersFromChatRoom(String roomCode);
}
