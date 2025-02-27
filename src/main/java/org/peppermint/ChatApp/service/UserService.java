package org.peppermint.ChatApp.service;

import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.User;

public interface UserService {
    User registerUser(User user);
    User getUser(String id);
    User updateUser(User user);
    void deleteUser(String id);
    User changeEmail(String email);
    User changePassword(String password);
    User changeUsername(String username);
    User changeDob(String dob);
}
