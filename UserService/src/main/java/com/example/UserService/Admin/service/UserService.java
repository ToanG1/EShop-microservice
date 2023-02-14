package com.example.UserService.Admin.service;

import com.example.UserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void deleteUser(String uid) {
        userRepository.findByUid(uid).ifPresentOrElse(
                user -> {
                    userRepository.delete(user);
                    log.info("User {} is deleted successfully", uid);
                },
                () -> log.info("User {} is deleted successfully", uid)
        );
    }
}
