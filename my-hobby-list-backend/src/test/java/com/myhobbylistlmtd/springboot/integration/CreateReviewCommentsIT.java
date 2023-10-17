package com.myhobbylistlmtd.springboot.integration;

import org.apache.commons.lang3.StringUtils;
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
import com.myhobbylistlmtd.springboot.request.body.RequestCreateReviewComment;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.utils.CreateReviewCommentsConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { CreateReviewCommentsConfiguration.class })
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateReviewCommentsIT {
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
  public void createReviewWithSuccess() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("dwdad");
    body.setMediaId(Long.valueOf(1));
    body.setUsernameFromReview("Victor");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);
    
    LocalDate dateObj = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String date = dateObj.format(formatter);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)).andExpect(status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)))
      .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Teste12345"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.commentary").value("dwdad"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.edited").value(false))
      .andExpect(MockMvcResultMatchers.jsonPath("$.insertionDate").value(date));
  }

  @Test
  public void errorWhenReviewDoesntExists() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("dwdad");
    body.setMediaId(Long.valueOf(999));
    body.setUsernameFromReview("Victor");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)
    )
    .andExpect(status().isNotFound())
    .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Review não encontrada!"));

    body.setUsernameFromReview("adwaiudwgai");
    body.setMediaId(Long.valueOf(1));
    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body))
      .header("Authorization", token)).andExpect(status().isNotFound()
    )
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Review não encontrada!"));
  }

  @Test
  public void errorWhenCommentaryIsNull() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setMediaId(Long.valueOf(1));
    body.setUsernameFromReview("Victor");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)).andExpect(status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("commentary é obrigatório"));

    body.setCommentary(null);
    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body))
      .header("Authorization", token)).andExpect(status().isBadRequest()
    )
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("commentary é obrigatório"));
  }

  @Test
  public void errorWhenCommentarySizeIsWrong() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("");
    body.setMediaId(Long.valueOf(1));
    body.setUsernameFromReview("Victor");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)).andExpect(status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("comentário deve ter um length entre 1 e 1000"));

    body.setCommentary(StringUtils.repeat("*", 1001));
    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body))
      .header("Authorization", token)).andExpect(status().isBadRequest()
    )
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("comentário deve ter um length entre 1 e 1000"));
  }

  @Test
  public void errorWhenUsernameFromReviewIsNull() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("dwdad");
    body.setMediaId(Long.valueOf(1));
    // body.setUsernameFromReview("Victor");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)).andExpect(status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("usernameFromReview é obrigatório"));

    body.setUsernameFromReview(null);
    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body))
      .header("Authorization", token)).andExpect(status().isBadRequest()
    )
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("usernameFromReview é obrigatório"));
  }

  @Test
  public void errorWhenUsernameFromReviewSizeIsWrong() throws Exception {
    RequestCreateReviewComment body = new RequestCreateReviewComment();
    body.setCommentary("dwadw");
    body.setMediaId(Long.valueOf(1));
    body.setUsernameFromReview("");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(json)
      .header("Authorization", token)).andExpect(status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("nome do usuário deve ter um length entre 1 e 20"));

    body.setCommentary(StringUtils.repeat("*", 21));
    mockMvc.perform(post("/review-comments/create")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(body))
      .header("Authorization", token)).andExpect(status().isBadRequest()
    )
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("nome do usuário deve ter um length entre 1 e 20"));
  }
}
