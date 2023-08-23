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
import com.myhobbylistlmtd.springboot.utils.LoginTestConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { LoginTestConfiguration.class })
@ActiveProfiles({ "test" })
public class FindUserProfileTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findProfileWithSucess() throws Exception {
    String todayDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    ResultActions response = mockMvc.perform(get("/user/profile/Victor"));
    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(4)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.userDescription").value(""))
        .andExpect(MockMvcResultMatchers.jsonPath("$.profileImage").value("images/profile/default.jpeg"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Victor"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.joinedAt").value(todayDate));
  }

  @Test
  public void findUserThatDoesntExists() throws Exception {
    ResultActions response = mockMvc.perform(get("/user/profile/fvawfafwafwa"));
    response.andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User não encontrada!"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404));
  }
}
