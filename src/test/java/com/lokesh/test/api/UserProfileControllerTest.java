package com.lokesh.test.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokesh.test.BaseTest;
import com.lokesh.test.entity.Profile;
import com.lokesh.test.entity.User;
import com.lokesh.test.service.UserProfileService;
import com.lokesh.test.util.FileUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserProfileControllerTest extends BaseTest {

  @Mock private UserProfileService userProfileService;
  @InjectMocks private UserProfileController userProfileController;
  @Autowired ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
  }

  @Test
  public void testCreateUserProfile() throws Exception {
    User user =
        objectMapper.readValue(FileUtil.loadJsonFile("create-user-profile.json"), User.class);
    ResponseEntity expectedResponse = new ResponseEntity<>(HttpStatus.CREATED);
    when(userProfileService.createUserProfile(user)).thenReturn(expectedResponse);

    mockMvc
        .perform(
            post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FileUtil.loadJsonFile("create-user-profile.json")))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void testGetUserProfile() throws Exception {
    Profile profile =
        objectMapper.readValue(FileUtil.loadJsonFile("get-user-profile.json"), Profile.class);
    ResponseEntity<Profile> expectedResponse = new ResponseEntity<>(profile, HttpStatus.OK);
    when(userProfileService.getUserProfile(1L)).thenReturn(expectedResponse);

    mockMvc
        .perform(get("/api/v1/users/1/profile").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.firstName", is("string")));
  }

  @Test
  public void testUpdateUserProfile() throws Exception {
    Profile profile =
        objectMapper.readValue(FileUtil.loadJsonFile("update-user-profile.json"), Profile.class);
    ResponseEntity expectedResponse = new ResponseEntity<>(HttpStatus.NO_CONTENT);
    when(userProfileService.updateUserProfile(1L, profile)).thenReturn(expectedResponse);

    mockMvc
        .perform(
            put("/api/v1/users/1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(FileUtil.loadJsonFile("update-user-profile.json")))
        .andExpect(status().is2xxSuccessful());
  }

  @Test
  public void testDeleteUserProfile() throws Exception {
    ResponseEntity<Profile> expectedResponse = new ResponseEntity<>(HttpStatus.OK);
    when(userProfileService.getUserProfile(1L)).thenReturn(expectedResponse);

    mockMvc
        .perform(delete("/api/v1/users/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is2xxSuccessful());
  }
}
