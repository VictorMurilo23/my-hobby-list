package com.myhobbylistlmtd.springboot.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.myhobbylistlmtd.springboot.MyHobbyListBackendApplication;
import com.myhobbylistlmtd.springboot.utils.FindReviewsConfiguration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { FindReviewsConfiguration.class })
@ActiveProfiles({ "test" })
public class FindReviewsTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findCharactersWithSuccess() throws Exception {
    ResultActions response = mockMvc.perform(get("/reviews/find/1"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews", hasSize(10)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2));
  }

  @Test
  public void findCharactersPages() throws Exception {
    mockMvc.perform(get("/reviews/find/1?page=0"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews", hasSize(10)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2));
    mockMvc.perform(get("/reviews/find/1?page=1"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews", hasSize(6)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2));
    mockMvc.perform(get("/reviews/find/1?page=2"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.reviews", hasSize(0)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(2));
  }
}
