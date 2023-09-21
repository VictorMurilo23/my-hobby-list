package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.myhobbylistlmtd.springboot.characters.Characters;
import com.myhobbylistlmtd.springboot.characters.role.CharactersRole;
import com.myhobbylistlmtd.springboot.media.characters.MediaCharacters;
import com.myhobbylistlmtd.springboot.media.characters.MediaCharactersRepository;
import com.myhobbylistlmtd.springboot.media.characters.MediaCharactersService;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.objs.MediaParams;

@ExtendWith(MockitoExtension.class)
public class MediaCharactersServiceTest {
  private MediaCharactersService service;

  @Mock
  private MediaCharactersRepository mediaCharactersRepo;

  @Mock
  private MediaService mediaService;

  @BeforeEach
  void setUp() {
    this.service = new MediaCharactersService(mediaService, mediaCharactersRepo);
  }

  @Test
  public void getCharactersWithSuccess() {
    Media media = new Media(new MediaParams("Teste1"));
    Long mediaId = Long.valueOf(4);
    ReflectionTestUtils.setField(media, "id", mediaId);
    Characters characterOne = new Characters("Personagem1", "Info1");
    Characters characterTwo = new Characters("Personagem2", "Info2");
    Characters characterThree = new Characters("Personagem3", "Info3");

    CharactersRole roleOne = new CharactersRole("Protagonista");
    CharactersRole roleTwo = new CharactersRole("Antagonista");

    MediaCharacters mediaCharacterOne = new MediaCharacters(media, characterOne, roleOne);
    MediaCharacters mediaCharacterTwo = new MediaCharacters(media, characterTwo, roleOne);
    MediaCharacters mediaCharacterThree = new MediaCharacters(media, characterThree, roleTwo);

    when(mediaService.findByName(anyString())).thenReturn(media);
    when(mediaCharactersRepo.findAllById_MediaId(anyLong())).thenReturn(Arrays.asList(mediaCharacterOne, mediaCharacterTwo, mediaCharacterThree));

    List<MediaCharacters> serviceReturn = this.service.getCharacters("Teste");

    verify(mediaCharactersRepo).findAllById_MediaId(mediaId);
    verify(mediaService).findByName("Teste");
    assertThat(serviceReturn.size(), is(3));
  }
}
