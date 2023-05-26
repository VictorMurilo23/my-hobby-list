package com.myhobbylistlmtd.springboot.objs;

import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.type.MediaType;
import com.myhobbylistlmtd.springboot.request.body.RequestMediaBody;

public class MediaParams extends RequestMediaBody {
  /** Duração da media, podem ser horas, capítulos ou episódios.
  * @param mediaName Nome da media
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams(final String mediaName) {
    setName(mediaName);
  }

  /** Duração da media, podem ser horas, capítulos ou episódios.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private Integer length;

  /** Quantidade de volumes da media, se a media não tiver volumes,
  * deixa null mesmo.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private Integer volumes = null;

  /** Url com a imagem da capa da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private String imageUrl;

  /** Objeto de status da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private MediaStatus status;

  /** Objeto de tipo da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private MediaType type;

  /** Getter de length.
  * @return Um int com a duração da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Integer getLength() {
    return length;
  }

  /** Setter de length.
  * @param newLength Um int com a nova duração da media.
  * @return Retorna o próprio objeto, assim é possível encadear vários setters
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams setLength(final Integer newLength) {
    this.length = newLength;
    return this;
  }

  /** Getter de volumes.
  * @return Um int com a quantidade de volumes da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public Integer getVolumes() {
    return volumes;
  }

  /** Setter de volumes.
  * @param volumesQuant Um int com a nova quantidade de volumes da media.
  * @return Retorna o próprio objeto, assim é possível encadear vários setters
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams setVolumes(final Integer volumesQuant) {
    this.volumes = volumesQuant;
    return this;
  }

  /** Getter de imageUrl.
  * @return Uma string com a url da capa da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getImageUrl() {
    return imageUrl;
  }

  /** Setter de imageUrl.
  * @param newImageUrl Uma string com a nova url da capa da media.
  * @return Retorna o próprio objeto, assim é possível encadear vários setters
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams setImageUrl(final String newImageUrl) {
    this.imageUrl = newImageUrl;
    return this;
  }

  /** Getter de status.
  * @return Um objeto de MediaStatus.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaStatus getStatus() {
    return status;
  }

  /** Setter de status.
  * @param newStatus Um objeto com novas informações de MediaStatus.
  * @return Retorna o próprio objeto, assim é possível encadear vários setters
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams setStatus(final MediaStatus newStatus) {
    this.status = newStatus;
    return this;
  }

  /** Getter de type.
  * @return Um objeto de MediaType.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaType getType() {
    return type;
  }

    /** Setter de Type.
  * @param newType Um objeto com novas informações de MediaType.
  * @return Retorna o próprio objeto, assim é possível encadear vários setters
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public MediaParams setType(final MediaType newType) {
    this.type = newType;
    return this;
  }
}
