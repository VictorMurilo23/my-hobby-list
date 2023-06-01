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
import com.myhobbylistlmtd.springboot.utils.MediaTestConfiguration;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
@Import(MediaTestConfiguration.class)
public class MediaControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findByIdWithValidId() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/search-by-id/1"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tessst"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("capa/capa"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status.status").value("Completo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.type.type").value("Manga"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.volumes").value(25));
  }

  @Test
  public void findByName() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/search-by-name/tes"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[0].name").value("Tessst"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[1].name").value("Tes1"));
  }

  @Test
  public void getRecentAdd() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/recent-add"));
    String[] correctDateOrder = {
        "2024-05-29T08:01:00", "2023-09-19T08:01:00", "2023-06-01T08:01:00", "2023-05-30T09:01:00",
        "2023-05-30T08:01:00", "2023-05-29T08:01:00", "2023-05-10T08:05:00", "2023-05-03T08:01:00",
        "2023-04-20T08:01:00",
        "2023-01-03T08:01:00" };

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias", hasSize(10)));
    for (int index = 0; index < correctDateOrder.length; index += 1) {
      String currentItem = String.format("$.medias[%s].insertionDate", index);
      response.andExpect(MockMvcResultMatchers.jsonPath(currentItem).value(correctDateOrder[index]));
    }

  }
}
