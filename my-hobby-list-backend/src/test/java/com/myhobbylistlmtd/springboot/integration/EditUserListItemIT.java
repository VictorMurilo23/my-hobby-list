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
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.utils.EditUserListItemConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.commons.lang3.StringUtils;

import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = { EditUserListItemConfiguration.class })
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EditUserListItemIT {
  @Autowired
  private MockMvc mockMvc;

  private String token;

  @BeforeAll
  public void beforeAllTests() throws Exception {
    String json = "{ \"email\": \"email@gmail.com\", \"password\": \"123\" }";

    MvcResult response = mockMvc.perform(post("/user/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andReturn();
    this.token = JsonPath.read(response.getResponse().getContentAsString(), "$.token");
  }

  @Test
  public void throwErrorWhenTokenIsInvalid() throws Exception {
    mockMvc.perform(patch("/list/edit")
        .header("Authorization", "token inválido")).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Token inválido"));
  }

  @Test
  public void throwErrorWhenMediaIdIsNull() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(null);
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setScore(1);
    body.setProgress(500);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("mediaId é obrigatório"));
  }

  @Test
  public void throwErrorWhenScoreIsGreaterThanTen() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setScore(11);
    body.setProgress(500);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("score deve ser um valor abaixo de 11"));
  }

  @Test
  public void throwErrorWhenScoreIsLessThanOne() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setScore(0);
    body.setProgress(500);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("score deve ser um valor acima de 0"));
  }

  @Test
  public void throwErrorWhenProgressIsLessThanZero() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setProgress(-1);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("progress deve ser maior ou igual a 0"));
  }

  @Test
  public void throwErrorWhenStatusLengthIsIncorrect() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setProgress(11);
    body.setStatus(StringUtils.repeat("*", 101));
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("status deve ser uma string de 1 a 100 caracteres"));

    body.setStatus("");
    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("status deve ser uma string de 1 a 100 caracteres"));
  }
  
  @Test
  public void throwErrorWhenNotesLengthIsIncorrect() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes(StringUtils.repeat("*", 1001));
    body.setProgress(11);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("notes deve ser uma string de 1 a 1000 caracteres"));

    body.setNotes("");
    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(body))
        .header("Authorization", this.token)).andExpect(status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("notes deve ser uma string de 1 a 1000 caracteres"));
  }

  @Test
  public void editAllValuesWithSuccess() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    body.setScore(1);
    body.setProgress(500);
    body.setStatus("Droppado");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.progress").value(500))
        .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("dawdwadwadadfafwafjafjwai"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Droppado"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.image").value("capa/capa"));
  }

  @Test
  public void editOnlyProgressWithSuccess() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setProgress(500);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.progress").value(500))
        .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("blablal"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Em andamento"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.image").value("capa/capa"));
  }

  @Test
  public void editOnlyNotesWithSuccess() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setNotes("dawdwadwadadfafwafjafjwai");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.progress").value(500))
        .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("dawdwadwadadfafwafjafjwai"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Em andamento"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.image").value("capa/capa"));
  }

  @Test
  public void editOnlyScoreWithSuccess() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setScore(8);
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.progress").value(500))
        .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(8))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("dawdwadwadadfafwafjafjwai"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Droppado"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.image").value("capa/capa"));
  }

  @Test
  public void editOnlyStatusWithSuccess() throws Exception {
    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(Long.valueOf(1));
    body.setStatus("Em andamento");
    ObjectMapper objectMapper = new ObjectMapper();
    String json = objectMapper.writeValueAsString(body);

    mockMvc.perform(patch("/list/edit")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json)
        .header("Authorization", this.token)).andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(5)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.progress").value(500))
        .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(8))
        .andExpect(MockMvcResultMatchers.jsonPath("$.notes").value("dawdwadwadadfafwafjafjwai"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Em andamento"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.*", hasSize(3)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.media.image").value("capa/capa"));
  }
}
