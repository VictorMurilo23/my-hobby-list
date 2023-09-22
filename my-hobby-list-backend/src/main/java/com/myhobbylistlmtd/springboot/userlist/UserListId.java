package com.myhobbylistlmtd.springboot.userlist;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
* Chave primária composta de UserList.
* @since 1.0
* @author Victor Murilo
* @version 1.0
*/
@Embeddable
public class UserListId implements Serializable {

  /**
  * Chave referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "media_id")
  @JsonView(Views.Public.class)
  @JsonValue
  private Media mediaId;

  /**
  * Chave referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "user_id")
  @JsonView(Views.Internal.class)
  private User userId;

    /**
  * Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public UserListId() {
  }

  /**
  * Default constructor.
  * @param userId Id referente ao usuário.
  * @param mediaId Id referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public UserListId(final User userId, final Media mediaId) {
    this.userId = userId;
    this.mediaId = mediaId;
  }

  /**
   * Getter do atributo media.
   * @return Um objeto de media
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Media getMediaId() {
    return mediaId;
  }

    /**
   * Getter do atributo user.
   * @return Um objeto de User
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public User getUserId() {
    return userId;
  }

  /**
   * Cria uma hash do objeto atual.
   * @return Uma hash.
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public int hashCode() {
    return Objects.hash(userId, mediaId);
  }

    /**
   * Getter do atributo user.
   * @return Um objeto de User
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserListId other = (UserListId) obj;
    return Objects.equals(userId, other.userId)
    && Objects.equals(mediaId, other.mediaId);
  }
}
