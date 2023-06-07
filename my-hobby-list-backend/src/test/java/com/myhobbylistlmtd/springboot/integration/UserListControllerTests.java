package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.utils.LoginTestConfiguration;
import com.myhobbylistlmtd.springboot.utils.MediaTestConfiguration;
import com.myhobbylistlmtd.springboot.utils.UserListItemStatusTestConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {LoginTestConfiguration.class, MediaTestConfiguration.class, UserListItemStatusTestConfiguration.class})
@ActiveProfiles({ "test" })
public class UserListControllerTests {
  
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testReqWithoutUserId() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    Long mediaId = Long.valueOf(1);
    body.setMediaId(mediaId);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/list/insert")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("userId é obrigatório"));
  }

  @Test
  public void testReqWithoutMediaId() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    Long userId = Long.valueOf(1);
    body.setUserId(userId);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/list/insert")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("mediaId é obrigatório"));
  }

  @Test
  public void testScoreLessThanZero() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(1);
    body.setUserId(userId);
    body.setMediaId(mediaId);
    body.setScore(-1);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/list/insert")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("score deve ser um valor acima de 0"));
  }

  @Test
  public void testScoreDiffFromNum() throws Exception {
    String json = "{\"mediaId\": 1, \"userId\": 1, \"score\": \"dawdwadad\"}";

    ResultActions response = mockMvc.perform(post("/list/insert")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("score deve ser um campo válido"));
  }

  @Test
  public void testSucessInsert() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    Long userId = Long.valueOf(1);
    Long mediaId = Long.valueOf(4);
    body.setUserId(userId);
    body.setMediaId(mediaId);
    body.setStatus("Em andamento");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/list/insert")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item inserido com sucesso"));
  }
}
