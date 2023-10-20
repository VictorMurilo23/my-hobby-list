package com.myhobbylistlmtd.springboot.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.objs.AllImagesUrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/images")
@Tag(name = "Images")
public class ImageController {
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
  @Operation(summary = "Pega a imagem da capa da mídia")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna uma imagem, podendo ser um .jpg ou .png",
      content = {
        @Content(
          mediaType = "image/jpeg",
          schema = @Schema(type = "string", format = "binary")
        ),
        @Content(
          mediaType = "image/png",
          schema = @Schema(type = "string", format = "binary")
        )
      }
    )
  })
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
  @Operation(summary = "Pega uma imagem de perfil")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna uma imagem, podendo ser um .jpg ou .png",
      content = {
        @Content(
          mediaType = "image/jpeg",
          schema = @Schema(type = "string", format = "binary")
        ),
        @Content(
          mediaType = "image/png",
          schema = @Schema(type = "string", format = "binary")
        )
      }
    ),
  })
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
  @Operation(summary = "Pega todas as imagens de perfil disponíveis")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com a url de todas as imagens de "
      + "perfil disponíveis",
      content = {
        @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = AllImagesUrl.class)
        )
      }
    ),
  })
  public AllImagesUrl getAllProfileImage() {
    String filesPath = this.imagePathRoot + "profile";
    AllImagesUrl imagesUrl = service.allImagesUrl(
      filesPath, "images/profile"
    );
    return imagesUrl;
  }

  /**
   * Rota estática de achar uma imagem de um personagem.
   * @param imageName Nome da imagem
   * @param mediaName Nome da media
   * @return Uma imagem png ou jpg.
   * @throws NotFoundException Ocorre quando a imagem não é encontrada.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/characters/{mediaName}/{imageName}")
  @Operation(summary = "Pega uma imagem do personagem")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna uma imagem, podendo ser um .jpg ou .png",
      content = {
        @Content(
          mediaType = "image/jpeg",
          schema = @Schema(type = "string", format = "binary")
        ),
        @Content(
          mediaType = "image/png",
          schema = @Schema(type = "string", format = "binary")
        )
      }
    ),
  })
  public ResponseEntity<byte[]> getCharactersImage(
    @PathVariable("imageName") final String imageName,
    @PathVariable("mediaName") final String mediaName
  ) {
    System.out.println(this.imagePathRoot + "characters/" + mediaName + "/" + imageName);
    byte[] image = service.findImage(
      this.imagePathRoot + "characters/" + mediaName + "/" + imageName
    );
    MediaType type = imageName.endsWith(".jpg")
    ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
    return ResponseEntity.ok().contentType(type).body(image);
  }
}
