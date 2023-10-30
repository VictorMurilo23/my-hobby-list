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
import com.myhobbylistlmtd.springboot.utils.FindUserListConfiguration;

// import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {FindUserListConfiguration.class})
@ActiveProfiles({ "test" })
public class FindUserListIT {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testSucessFindUserList() throws Exception {
    ResultActions response = mockMvc.perform(get("/list/find/Teste12345"));
    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.list", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].progress").value(12))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].score").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].notes").value("Muito bom"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].status").value("Em andamento"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.image").value("capa/capa"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].progress").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].score").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].notes").value("Pior coisa que eu já vi na minha vida"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].status").value("Droppado"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].media.id").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].media.name").value("Tes2"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[1].media.image").value("capa/capa"));
  }

  @Test
  public void errorWhenUsernameNotFound() throws Exception {
    ResultActions response = mockMvc.perform(get("/list/find/ehawudhawiudhawoiuh"));
    response.andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User não encontrada!"));
  }

  @Test
  public void errorWhenStatusNameNotFound() throws Exception {
    ResultActions response = mockMvc.perform(get("/list/find/Teste12345?statusName=inexistente"));
    response.andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Nome de status inválido"));
  }

  @Test
  public void sucessWhenStatusNameIsValid() throws Exception {
    ResultActions response = mockMvc.perform(get("/list/find/Teste12345?statusName=Em andamento"));
    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.list", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].progress").value(12))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].score").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].notes").value("Muito bom"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].status").value("Em andamento"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.list[0].media.image").value("capa/capa"));
  }
}
