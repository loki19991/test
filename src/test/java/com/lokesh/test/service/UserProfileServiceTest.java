package com.lokesh.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokesh.test.BaseTest;
import com.lokesh.test.Exception.UserNotFoundException;
import com.lokesh.test.entity.Profile;
import com.lokesh.test.entity.User;
import com.lokesh.test.repository.UserRepository;
import com.lokesh.test.util.FileUtil;
import java.util.Optional;
import net.javacrumbs.jsonunit.JsonAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.core.Option.TREATING_NULL_AS_ABSENT;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserProfileServiceTest extends BaseTest {

  @Mock private UserRepository userRepository;
  @InjectMocks private UserProfileService userProfileService;
  @Autowired ObjectMapper objectMapper;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testCreateUserProfile() throws Exception {
    User user =
        objectMapper.readValue(FileUtil.loadJsonFile("create-user-profile.json"), User.class);
    when(userRepository.save(user)).thenReturn(user);
    ResponseEntity response = userProfileService.createUserProfile(user);
    Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.CREATED.value());
  }

  @Test
  public void testGetUserProfile() throws Exception {
    User user = objectMapper.readValue(FileUtil.loadJsonFile("get-user.json"), User.class);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    ResponseEntity<Profile> response = userProfileService.getUserProfile(1L);

    assertJsonEquals(
        objectMapper.writeValueAsString(user.getProfile()),
        objectMapper.writeValueAsString(response.getBody()),
        JsonAssert.when(TREATING_NULL_AS_ABSENT));
  }

  @Test(expected = UserNotFoundException.class)
  public void testGetUserProfileThrowsUserNotFoundException() throws Exception {
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    userProfileService.getUserProfile(1L);
  }

  @Test
  public void testUpdateUserProfile() throws Exception {
    User user = objectMapper.readValue(FileUtil.loadJsonFile("get-user.json"), User.class);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    ResponseEntity response = userProfileService.updateUserProfile(1L, user.getProfile());
    Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.NO_CONTENT.value());
  }

  @Test(expected = UserNotFoundException.class)
  public void testUpdatetUserProfileThrowsUserNotFoundException() throws Exception {
    User user = objectMapper.readValue(FileUtil.loadJsonFile("get-user.json"), User.class);
    when(userRepository.findById(1L)).thenReturn(Optional.empty());
    userProfileService.updateUserProfile(1L, user.getProfile());
  }

  @Test
  public void testDeleteUserProfile() {
    doNothing().when(userRepository).deleteById(1L);
    ResponseEntity response = userProfileService.deleteUserProfile(1L);
    Assert.assertTrue(response.getStatusCodeValue() == HttpStatus.OK.value());
  }
}
