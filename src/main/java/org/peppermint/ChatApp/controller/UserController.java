package org.peppermint.ChatApp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.peppermint.ChatApp.configuration.securityconfig.SecurityConstants;
import org.peppermint.ChatApp.dto.UserDTO;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@AllArgsConstructor
@RestController
public class UserController {
    public UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user, HttpServletResponse response) {
        User createdUser = userService.registerUser(user);
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.setHeader(SecurityConstants.JWT_HEADER, SecurityConstants.BEARER + token);
        UserDTO userDTO = mapToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
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
