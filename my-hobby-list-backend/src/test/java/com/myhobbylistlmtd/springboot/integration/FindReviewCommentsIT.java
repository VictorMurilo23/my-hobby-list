package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.utils.FindReviewCommentsConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
@Import(FindReviewCommentsConfiguration.class)
public class FindReviewCommentsIT {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findReviewCommentsWithSuccess() throws Exception {
    ResultActions response = mockMvc.perform(get("/review-comments/find/Victor/1"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].commentary").value("Muito bom"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].edited").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[0].username").value("Victor"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].commentary").value("Muito bom"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].edited").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.comments[1].username").value("Victo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.*", hasSize(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.content").value("Teste"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.edited").value(false))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.recommended").value(true))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.user.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.review.user.username").value("Victor"))
        
        ;
  }

  @Test
  public void findReviewCommentsErrorWhenMediaIdIsntANumber() throws Exception {
    ResultActions response = mockMvc.perform(get("/review-comments/find/Victor/awd"));

    response.andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Insira um mediaId em formato de número"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
    
    mockMvc.perform(get("/review-comments/find/Victor/true"))
        .andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Insira um mediaId em formato de número"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400));
  }
}
