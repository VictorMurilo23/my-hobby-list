package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;
import com.myhobbylistlmtd.springboot.utils.EditReviewsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { EditReviewsConfiguration.class })
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditReviewsIT {
  @Autowired
  private MockMvc mockMvc;

  private String token;

  @BeforeAll
  public void beforeAllTests() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("email@gmail.com");
    body.setPassword("123");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    MvcResult response = mockMvc.perform(post("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andReturn();
    this.token = JsonPath.read(response.getResponse().getContentAsString(), "$.token");
  }

  @Test
  public void errorWhenTokenIsInvalid() throws Exception {
    String json = "{\"mediaId\": 1, \"content\": \"Teste\"}";
    mockMvc.perform(patch("/reviews/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", "token-inválido"))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token inválido"));
  }

  @Test
  public void editReviewContentWithSucess() throws Exception {
    String json = "{\"mediaId\": 1, \"content\": \"Teste\"}";

    mockMvc.perform(patch("/reviews/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Teste"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.edited").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.recommended").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Victor"));
  }

  @Test
  public void editReviewRecommendedWithSucess() throws Exception {
    String json = "{\"mediaId\": 1, \"recommended\": false}";

    mockMvc.perform(patch("/reviews/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.edited").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.recommended").value("false"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Victor"));
  }

  @Test
  public void editReviewContentAndRecommendedWithSucess() throws Exception {
    String json = "{\"mediaId\": 1, \"recommended\": true, \"content\": \"Teste12345\"}";

    mockMvc.perform(patch("/reviews/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.edited").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.recommended").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Teste12345"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.user.username").value("Victor"));
  }

  @Test
  public void expectErrorWhenRecommendedIsDifferentFromBoolean() throws Exception {
    String json = "{\"mediaId\": 1, \"recommended\": \"fa\", \"content\": \"Teste12345\"}";

    mockMvc.perform(patch("/reviews/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("recommended deve ser um campo válido"));
  }
}
