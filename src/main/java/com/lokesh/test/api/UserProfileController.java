package com.lokesh.test.api;

import com.lokesh.test.entity.Profile;
import com.lokesh.test.entity.User;
import com.lokesh.test.model.SuccessfulResponse;
import com.lokesh.test.service.UserProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Api(tags = {"Profile"})
@RequestMapping("/api/v1")
public class UserProfileController implements ProfileApi{

    private final HttpServletRequest request;

    @Autowired
    public UserProfileController(HttpServletRequest request) {
        this.request = request;
    }

    @Autowired
    UserProfileService userProfileService;

    public ResponseEntity<SuccessfulResponse> addUser(@Valid @RequestBody User user) {
        return userProfileService.createUserProfile(user);
    }

    public ResponseEntity<Void> deleteUser(
            @ApiParam(value = "user id", required = true)
            @PathVariable("id") Long userId) {
        return userProfileService.deleteUserProfile(userId);
    }

    public ResponseEntity<Profile> getUserProfile(
            @ApiParam(value = "user id", required = true)
            @PathVariable("id") Long userId) {
        return userProfileService.getUserProfile(userId);
    }

    public ResponseEntity<Void> updateProfile(
            @ApiParam(value = "user id", required = true)
            @PathVariable("id") Long userId,
            @Valid @RequestBody Profile profile) {
        return userProfileService.updateUserProfile(userId, profile);
    }
}
