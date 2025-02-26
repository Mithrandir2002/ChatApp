package org.peppermint.ChatApp.service.serviceimpl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.peppermint.ChatApp.exception.EntityNotFoundException;
import org.peppermint.ChatApp.exception.UserExistedException;
import org.peppermint.ChatApp.model.ChatRoom;
import org.peppermint.ChatApp.model.Member;
import org.peppermint.ChatApp.model.User;
import org.peppermint.ChatApp.repository.ChatRoomRepository;
import org.peppermint.ChatApp.repository.MemberRepository;
import org.peppermint.ChatApp.repository.UserRepository;
import org.peppermint.ChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    MemberRepository memberRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(User user) {
        if(userRepository.findUserByEmail(user.getEmail()).isPresent()) throw new UserExistedException("Email " + user.getEmail() + " is already existed in our record");
        User savedUser = User.builder()
                .email(user.getEmail())
                .password(bCryptPasswordEncoder.encode(user.getPassword()))
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return userRepository.save(savedUser);
    }

    @Override
    public User getUser(String id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) return opUser.get();
        else throw new EntityNotFoundException(id, User.class);
    }

    @Override
    public User updateUser(User user) {
        Optional<User> opUser = userRepository.findById(user.getId());
        if (opUser.isEmpty()) throw new EntityNotFoundException(user.getId(), User.class);
        User savedUser = opUser.get();
        if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
            savedUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null && !user.getLastName().isEmpty()) {
            savedUser.setLastName(user.getLastName());
        }
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            Optional<User> existingUser = userRepository.findUserByUsername(user.getUsername());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
//                throw new UsernameExistedException("Username " + user.getUsername() + " already exists");
            }
            savedUser.setUsername(user.getUsername());
        }
        if (user.getDateOfBirth() != null) {
            savedUser.setDateOfBirth(user.getDateOfBirth());
        }
        return userRepository.save(savedUser);
    }

    @Transactional
    @Override
    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, User.class));
        memberRepository.deleteByUser(user);
        userRepository.deleteById(id);
    }


    @Override
    public User changeEmail(String email) {
        return null;
    }

    @Override
    public User changePassword(String password) {
        return null;
    }

    @Override
    public User changeUsername(String username) {
        return null;
    }

    @Override
    public User changeDob(String dob) {
        return null;
    }
}
