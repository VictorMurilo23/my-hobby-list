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
import com.myhobbylistlmtd.springboot.request.body.RequestEditReviewComment;
import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;
import com.myhobbylistlmtd.springboot.utils.EditReviewCommentsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { EditReviewCommentsConfiguration.class })
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditReviewCommentsIT {
  @Autowired
  private MockMvc mockMvc;

  private String token;

  @BeforeAll
  public void beforeAllTests() throws Exception {
    RequestLoginBody body = new RequestLoginBody();
    body.setEmail("emaifl@gmail.com");
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
    RequestEditReviewComment body = new RequestEditReviewComment();
    body.setCommentId(Long.valueOf(1));
    body.setCommentary("Teste");
    body.setMediaId(Long.valueOf(1));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", "token-inválido"))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token inválido"));
  }

  @Test
  public void errorWhenCommentIdIsInvalid() throws Exception {
    RequestEditReviewComment body = new RequestEditReviewComment();
    body.setCommentId(Long.valueOf(-1));
    body.setCommentary("Teste");
    body.setMediaId(Long.valueOf(1));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("commentId deve ser um número positivo"));

    body.setCommentId(null);
    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("commentId deve ser um valor válido"));
  }

  @Test
  public void errorWhenMediaIdIsInvalid() throws Exception {
    RequestEditReviewComment body = new RequestEditReviewComment();
    body.setCommentId(Long.valueOf(1));
    body.setCommentary("Teste");
    body.setMediaId(Long.valueOf(-1));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("mediaId deve ser um número positivo"));

    body.setMediaId(null);
    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("mediaId deve ser um valor válido"));
  }

  @Test
  public void errorWhenCommentaryIsInvalid() throws Exception {
    RequestEditReviewComment body = new RequestEditReviewComment();
    body.setCommentId(Long.valueOf(1));
    body.setCommentary("");
    body.setMediaId(Long.valueOf(1));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("comentário deve ter um length entre 1 e 1000"));

    body.setCommentary(" ".repeat(1001));
    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("comentário deve ter um length entre 1 e 1000"));

    body.setCommentary(null);
    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("commentary deve ser um valor válido"));
  }

  @Test
  public void editCommentWithSuccess() throws Exception {
    RequestEditReviewComment body = new RequestEditReviewComment();
    body.setCommentId(Long.valueOf(1));
    body.setCommentary("Comentário aleatório");
    body.setMediaId(Long.valueOf(1));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);
    LocalDate date = LocalDate.now();

    mockMvc.perform(patch("/review-comments/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.edited").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.insertionDate").value(date.toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Victor"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.commentId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.commentary").value("Comentário aleatório"));
  }
}
