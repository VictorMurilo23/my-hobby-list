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

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;

@RestController
@RequestMapping(value = "/images")
public class ImageController {
  // TODO Fazer duas rotas de imagens, uma de capas e outra pra personagens
  // TODO Reaproveitar a função de pegar imagem na rota dos personagens
  /**
   * Caminho base para o diretório de imagens.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private String imagePathRoot = "src/main/resources/images/";

  /**
   * Procura a imagem baseado no caminho passado por
   parâmetro e, se não achar, joga uma exceção.
   * @param path Caminho utilizado na busca da imagem.
   * @return Retorna a imagem em formato de bytes pronta para ser enviada
   * @throws NotFoundException Ocorre quando a imagem não
   é encontrada e retorna um http 404.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private byte[] findImage(final String path) throws NotFoundException {
    try {
      byte[] image = new byte[0];
      image = FileUtils.readFileToByteArray(
        new File(path)
      );
      return image;
    } catch (IOException e) {
      throw new NotFoundException("Imagem não encontrada");
    }
  }

  /**
   * Rota estática de imagens.
   * @param imageName Nome da imagem
   * @return Uma imagem png ou jpg.
   * @throws NotFoundException Ocorre quando a imagem não é encontrada.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/covers/{imageName}")
  public ResponseEntity<byte[]> getCoverImage(
    @PathVariable("imageName") final String imageName
  ) {
    byte[] image = this.findImage(this.imagePathRoot + "covers/" + imageName);
    MediaType type = imageName.endsWith(".jpg")
    ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
    return ResponseEntity.ok().contentType(type).body(image);
  }

  /**
   * Rota estática de achar uma imagem de perfil.
   * @param imageName Nome da imagem
   * @return Uma imagem png ou jpg.
   * @throws NotFoundException Ocorre quando a imagem não é encontrada.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/profile/{imageName}")
  public ResponseEntity<byte[]> getProfileImage(
    @PathVariable("imageName") final String imageName
  ) {
    byte[] image = this.findImage(this.imagePathRoot + "profile/" + imageName);
    MediaType type = imageName.endsWith(".jpg")
    ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
    return ResponseEntity.ok().contentType(type).body(image);
  }
}
