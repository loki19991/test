package com.lokesh.test.service;

import com.lokesh.test.Exception.UserNotFoundException;
import com.lokesh.test.entity.Profile;
import com.lokesh.test.entity.User;
import com.lokesh.test.model.SuccessfulResponse;
import com.lokesh.test.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserProfileService {

    @Autowired UserRepository userRepository;

    public ResponseEntity<SuccessfulResponse> createUserProfile(User user) {
        userRepository.save(user);
        SuccessfulResponse successfulResponse = new SuccessfulResponse();
        successfulResponse.setStatusCode(HttpStatus.CREATED.value());
        successfulResponse.setDescription("User object successfully created");
        return new ResponseEntity<>(successfulResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<Profile> getUserProfile(Long userId) {
        User user = findUserById(userId);
        return new ResponseEntity<>(user.getProfile(), HttpStatus.OK);
    }

    public ResponseEntity updateUserProfile(Long userId, Profile profile) {
        User user = findUserById(userId);
        user.setProfile(profile);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity deleteUserProfile(Long userId) {
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }

        log.error("User not found exception userId={}", userId);
        throw new UserNotFoundException("User not found exception");
    }
}
