package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.utils.LoginTestConfiguration;
import com.myhobbylistlmtd.springboot.utils.MediaTestConfiguration;
import com.myhobbylistlmtd.springboot.utils.UserListItemStatusTestConfiguration;

// import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { LoginTestConfiguration.class, MediaTestConfiguration.class,
    UserListItemStatusTestConfiguration.class })
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReviewsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  private String token;

  @BeforeAll
  public void beforeAllTests() throws Exception {
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("test@hotmail.com");
    body.setUsername("Teste12345");
    body.setPassword("1224");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    MvcResult response = mockMvc.perform(post("/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andReturn();
    this.token = JsonPath.read(response.getResponse().getContentAsString(), "$.token");
  }

  @Test
  public void createReviewWithoutValidToken() throws Exception {
    mockMvc.perform(post("/reviews/create")
        .header("Authorization", "token-inválido")).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token inválido"));

    mockMvc.perform(post("/reviews/create")
        .header("Authorization", 12334)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token inválido"));
  }

  @Test
  public void createReviewWithInvalidMediaIdFormat() throws Exception {
    String json = "{\"mediaId\": \"dawdwadad\"}";

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("mediaId deve ser um campo válido"));
  }

  @Test
  public void createReviewWithInvalidContent() throws Exception {
    String maxLenContent = " ".repeat(10001);
    String json = String.format("{\"mediaId\": 2, \"content\": \"%s\",  \"recommended\": false}", maxLenContent);

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("content deve ter um length entre 1 e 10000"));

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"mediaId\": 2, \"content\": \"\"}")
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("content deve ter um length entre 1 e 10000"));

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"mediaId\": 2}")
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("content deve ser um valor válido"));
  }

  @Test
  public void createReviewWithInvalidRecommended() throws Exception {
    String json = "{\"mediaId\": 2, \"content\": \"blabla\", \"recommended\": \"teste\"}";

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("recommended deve ser um campo válido"));

    mockMvc.perform(post("/reviews/create")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"mediaId\": 2, \"content\": \"q\"}")
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("recommended deve ser um campo válido"));
  }
}
