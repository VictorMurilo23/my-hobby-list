package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.image.ImageService;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {
  private ImageService imageService;

  @BeforeEach
  void setUp() {
    this.imageService = new ImageService();
  }

  @Test
  public void allImagesUrlShouldReturnAllImagesInAFolder(@TempDir File folder) throws IOException {
    File defaultImage = new File(folder, "default.png");
    defaultImage.createNewFile();
    File firstImage = new File(folder, "01.png");
    firstImage.createNewFile();

    List<String> imagesList = this.imageService.allImagesUrl(folder.getPath(), "images").getImages();

    assertThat(imagesList.size(), is(2));
    assertThat(imagesList.get(0), is("images/default.png"));
    assertThat(imagesList.get(1), is("images/01.png"));
  }

  @Test
  public void findImageWithSucess(@TempDir File folder) throws IOException {
    File defaultImage = new File(folder, "default.png");
    defaultImage.createNewFile();

    byte[] imagesList = this.imageService.findImage(folder.getPath() + "/default.png");

    assertThat(imagesList.length, is(0));
  }

  @Test
  public void findImageThrowsErrorWhenImageDoesntExists(@TempDir File folder) throws IOException {
    File defaultImage = new File(folder, "default.png");
    defaultImage.createNewFile();


    assertThrows(NotFoundException.class, () -> this.imageService.findImage(folder.getPath() + "/wdhaidgaigyd.png"));
  }
}
