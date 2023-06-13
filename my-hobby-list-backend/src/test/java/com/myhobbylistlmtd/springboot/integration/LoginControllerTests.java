package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.myhobbylistlmtd.springboot.utils.LoginTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@Import(LoginTestConfiguration.class)
@ActiveProfiles({ "test" })
public class LoginControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void throwsExceptionWhenInvalidEmailFormat() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("emaildwadad.com");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(get("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Insira um email com o formato válido!"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
  }

  @Test
  public void throwsExceptionWhenDoesntFoundEmailOnDb() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("email@dwadad.com");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(get("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isUnauthorized())
        .andExpect(result -> assertEquals("Senha ou email incorretos",
            result.getResolvedException().getMessage()))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
  }

  @Test
  public void throwsExceptionWhenPasswordDoesntMatchDbPassword() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("email@gmail.com");
    body.setPassword("errado");

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(get("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    response.andExpect(status().isUnauthorized())
        .andExpect(result -> assertEquals("Senha ou email incorretos",
            result.getResolvedException().getMessage()))
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException));
  }

  @Test
  public void returnTokenWhenSucess() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("email@gmail.com");
    body.setPassword("1456");

    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    ResultActions response = mockMvc.perform(get("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json));

    String expectedData = "TBA";
    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(expectedData));
  }
}