package org.peppermint.ChatApp.controller;

import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class UserController {
    public UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        return new ResponseEntity<>(mapToUserDTO(userService.registerUser(user)), HttpStatus.CREATED);
    }

    public UserDTO mapToUserDTO(User user) {
        UserDTO userDTO = UserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return userDTO;
    }
}
