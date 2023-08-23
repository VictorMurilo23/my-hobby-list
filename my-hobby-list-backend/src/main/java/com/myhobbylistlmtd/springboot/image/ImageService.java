package com.myhobbylistlmtd.springboot.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.objs.AllImagesUrl;

@Service
public class ImageService {
  /**
   * Encontra todas as imagens.
   * @param filesPath Caminho onde todas as imagens estão
   * @param url Url usada na hora de formar o caminho final da imagem
   * @return Um objeto contendo uma lista de urls de imagens
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public AllImagesUrl allImagesUrl(final String filesPath, final String url) {
    String[] pathnames = new File(filesPath).list();
    AllImagesUrl images = new AllImagesUrl();
    for (String pathname : pathnames) {
      images.setImage(String.format("%s/%s", url, pathname));
    }
    return images;
  }

  // TODO Reaproveitar a função de pegar imagem na rota dos personagens
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
  public byte[] findImage(final String path) throws NotFoundException {
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
}
