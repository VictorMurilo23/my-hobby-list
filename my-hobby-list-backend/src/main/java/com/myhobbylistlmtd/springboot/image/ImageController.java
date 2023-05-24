package com.myhobbylistlmtd.springboot.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.exceptions.ImageNotFoundException;

@RestController
@RequestMapping(value = "/images")
public class ImageController {
  // TODO Fazer duas rotas de imagens, uma de capas e outra pra personagens
  /**
   * Caminho base para o diretório de imagens.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private String imagePathRoot = "src/main/resources/images/";

  /**
   * Rota estática de imagens.
   * @param imageName Recebe um body contendo a senha, nome do usuário e o email
   * @return Uma imagem png ou jpg.
   * @throws ImageNotFoundException Ocorre quando a imagem não é encontrada.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("{imageName}")
  public ResponseEntity<byte[]> getImage(
      @PathVariable("imageName") final String imageName)
    throws ImageNotFoundException {
    byte[] image = new byte[0];
    try {
      image = FileUtils.readFileToByteArray(
        new File(imagePathRoot + imageName)
      );
    } catch (IOException e) {
      throw new ImageNotFoundException("Imagem não encontrada");
    }
    MediaType type = imageName.endsWith(".jpg")
    ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
    return ResponseEntity.ok().contentType(type).body(image);
  }
}
