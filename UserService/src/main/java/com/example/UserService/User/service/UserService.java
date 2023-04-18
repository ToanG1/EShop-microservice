package com.example.UserService.User.service;

import com.example.UserService.User.dto.User.Request.CreateUserRequest;
import com.example.UserService.User.dto.User.Request.UpdateUserRequest;
import com.example.UserService.User.dto.User.Response.UserResponse;
import com.example.UserService.model.User;
import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service("UserService")
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public void createUser(CreateUserRequest createUserRequest){
        User user = User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .uid(createUserRequest.getUid())
                .avatar(createUserRequest.getAvatar())
                .point(0)
                .role(0)
                .createAt(new Date())
                .updateAt(new Date())
                .build();
        userRepository.save(user);
        log.info("User {} is saved successfully", user.getId());
    }

    public UserResponse findUser(String uid) {
        Optional<User> user = userRepository.findByUid(uid);
        if (user.isPresent())
            return mapToUserResponse(user.get());
        else return null;
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .avatar(user.getAvatar())
                .point(user.getPoint())
                .uid(user.getUid())
                .role(user.getRole())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    public void updateUser(UpdateUserRequest updateUserRequest) {
        userRepository.findByUid(updateUserRequest.getUid()).ifPresentOrElse(
                user -> {
                    user.setName(updateUserRequest.getName() != null ? updateUserRequest.getName() : user.getName());
                    user.setEmail(updateUserRequest.getEmail() != null ? updateUserRequest.getEmail() : user.getEmail());
                    user.setPhoneNumber(user.getPhoneNumber() != null ?
                            updateUserRequest.getPhoneNumber() : user.getPhoneNumber());
                    user.setAvatar(updateUserRequest.getAvatar() != null ?
                            updateUserRequest.getAvatar() : user.getAvatar());
                    userRepository.save(user);
                    log.info("User {} is updated successfully", user.getUid());
                },
                () ->{
                    log.info("User {} is not available", updateUserRequest.getUid());
                }
        );
    }

    public UserResponse findUserByUsername(String username) {
        Optional<User> user = userRepository.findByName(username);
        if (user.isPresent())
            return mapToUserResponse(user.get());
        else return null;
    }
}
