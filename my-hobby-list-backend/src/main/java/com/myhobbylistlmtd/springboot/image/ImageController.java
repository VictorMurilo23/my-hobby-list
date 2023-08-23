package com.myhobbylistlmtd.springboot.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.objs.AllImagesUrl;

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
   * Service de image.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Autowired
  private ImageService service;

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
    byte[] image = service.findImage(
      this.imagePathRoot + "covers/" + imageName
    );
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
    byte[] image = service.findImage(
      this.imagePathRoot + "profile/" + imageName
    );
    MediaType type = imageName.endsWith(".jpg")
    ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
    return ResponseEntity.ok().contentType(type).body(image);
  }

  /**
   * Rota de encontrar todas as imagens de perfil disponíveis.
   * @return Uma lista com a url de todas as imagens disponíveis.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/profile-images")
  public AllImagesUrl getAllProfileImage() {
    String filesPath = this.imagePathRoot + "profile";
    AllImagesUrl imagesUrl = service.allImagesUrl(
      filesPath, "images/profile"
    );
    return imagesUrl;
  }
}
