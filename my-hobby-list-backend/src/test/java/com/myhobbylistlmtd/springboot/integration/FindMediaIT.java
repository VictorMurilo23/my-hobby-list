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

// import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MyHobbyListBackendApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
@Import(MediaTestConfiguration.class)
public class FindMediaIT {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void findByIdWithValidId() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/search-by-id/1"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(7)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Tessst"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(208))
        .andExpect(MockMvcResultMatchers.jsonPath("$.image").value("capa/capa"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Completo"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("Manga"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.volumes").value(25));
  }

  @Test
  public void findByName() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/search-by-name/tes"));

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[0].id").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[0].name").value("Tessst"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[0].image").value("capa/capa"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[1].id").value(3))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[1].name").value("Tes1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias[1].image").value("capa/capa"));
  }

  @Test
  public void getRecentAdd() throws Exception {
    ResultActions response = mockMvc.perform(get("/media/recent-add"));
    String[] correctMediasOrder = {
        "8888888899999", "oiytrcvb", "jjdjjwj", "Tessst",
        "tsste", "Tes1", "45445dwadawd", "vbnbvbbvng",
        "pjdpiwjpoidajhoi",
        "87dwdawdadw"
    };

    response.andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.medias", hasSize(10)));
    for (int index = 0; index < correctMediasOrder.length; index += 1) {
      String currentItem = String.format("$.medias[%s]", index);
      response
        .andExpect(MockMvcResultMatchers.jsonPath(currentItem + ".name").value(correctMediasOrder[index]))
        .andExpect(MockMvcResultMatchers.jsonPath(currentItem + ".*", hasSize(3)));
    }

  }
}
