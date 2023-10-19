package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.MediaService;

@ExtendWith(MockitoExtension.class)
public class MediaServiceTest {
  private MediaService mediaService;

  @Mock
  private MediaRepository mediaRepo;

  @BeforeEach
  void setUp() {
    this.mediaService = new MediaService(mediaRepo);
  }

  @Test
  public void getMostRecentMedias() {
    Media media1 = new Media("Teste1");
    Media media2 = new Media("Teste2");
    List<Media> recentMedias = new ArrayList<Media>();
    recentMedias.add(media1);
    recentMedias.add(media2);

    when(mediaRepo.findFirst10ByOrderByInsertionDateDesc()).thenReturn(recentMedias);

    List<Media> list = this.mediaService.getMostRecentMedias();

    verify(mediaRepo).findFirst10ByOrderByInsertionDateDesc();
    assertThat(list, contains(
        hasProperty("name", is("Teste1")),
        hasProperty("name", is("Teste2"))));
  }

  @Test
  public void findAllByName() {
    List<Media> mediaList = new ArrayList<Media>();
    mediaList.add(new Media("Nome muito interessante"));
    mediaList.add(new Media("Nome blablabal"));
    mediaList.add(new Media("Nome 3"));

    when(mediaRepo.findAllByNameContainingIgnoreCase("Nome")).thenReturn(mediaList);

    List<Media> list = this.mediaService.findAllByName("Nome");

    verify(mediaRepo).findAllByNameContainingIgnoreCase("Nome");
    assertThat(list, contains(
        hasProperty("name", is("Nome muito interessante")),
        hasProperty("name", is("Nome blablabal")),
        hasProperty("name", is("Nome 3"))));
  }

  @Test
  public void findByNameWithSucess() {
    Media media = new Media("Teste1");

    when(mediaRepo.findByName("Teste1")).thenReturn(media);

    Media serviceReturn = this.mediaService.findByName("Teste1");

    verify(mediaRepo).findByName("Teste1");
    assertThat(serviceReturn.getName(), is("Teste1"));
  }

  @Test()
  public void findByNameThrowsError() {
    when(mediaRepo.findByName("Inexistente")).thenReturn(null);

    assertThrows(NotFoundException.class, () -> this.mediaService.findByName("Inexistente"));

    verify(mediaRepo).findByName("Inexistente");
  }

  @Test
  public void findByIdWithSucess() {
    Long id = Long.valueOf(1);
    Optional<Media> media = Optional.of(new Media("Teste1"));

    when(mediaRepo.findById(Long.valueOf(id))).thenReturn(media);

    Media serviceReturn = this.mediaService.findById(id);

    verify(mediaRepo).findById(id);
    assertThat(serviceReturn.getName(), is("Teste1"));
  }

  @Test()
  public void findByIdThrowsError() {
    Long id = Long.valueOf(1);
    when(mediaRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> this.mediaService.findById(id));

    verify(mediaRepo).findById(id);
  }
}
