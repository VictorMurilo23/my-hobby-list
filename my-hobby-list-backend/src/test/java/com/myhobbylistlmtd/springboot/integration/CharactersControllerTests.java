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
import com.myhobbylistlmtd.springboot.utils.CharactersTestConfiguration;

// import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@Import(CharactersTestConfiguration.class)
@ActiveProfiles({ "test" })
public class CharactersControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findCharactersWithSuccess() throws Exception {
    ResultActions response = mockMvc.perform(get("/characters/Teste"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters[0].characterRole").value("Personagem principal"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters[0].character.name").value("Personagem1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters[1].characterRole").value("Personagem principal"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters[1].character.name").value("Personagem2"));
  }

  @Test
  public void returnErrorWhenMediaDoesntExists() throws Exception {
    ResultActions response = mockMvc.perform(get("/characters/bdawddahdb"));

    response.andExpect(status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Media não encontrada!"));
  }

  @Test
  public void returnEmptyArrayWhenMediaDoesntHaveCharacters() throws Exception {
    ResultActions response = mockMvc.perform(get("/characters/Teste3"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.characters", hasSize(0)));
  }
}
