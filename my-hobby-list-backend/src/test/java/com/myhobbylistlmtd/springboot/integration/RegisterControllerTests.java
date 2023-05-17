package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.utils.LoginTestConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@Import(LoginTestConfiguration.class)
public class RegisterControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void registerSucess() throws Exception {
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("email@.com");
    body.setUsername("Teste1");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    String expectedData = "TBA";
    response.andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedData));
  }

  @Test
  public void registerFailWhenEmailAlreadyInDB() throws Exception {
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("email@.com");
    body.setUsername("fawfa");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isConflict())
        .andExpect(result -> assertEquals("409 CONFLICT \"O email email@.com já está em uso.\"",
            result.getResolvedException().getMessage()))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
  }

  @Test
  public void registerFailWhenUsernameAlreadyInDB() throws Exception {
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("email@trest.com");
    body.setUsername("Teste1");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(post("/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isConflict())
        .andExpect(result -> assertEquals("409 CONFLICT \"O nome Teste1 já está em uso.\"",
            result.getResolvedException().getMessage()))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
  }
}
