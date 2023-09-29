package com.myhobbylistlmtd.springboot.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.utils.Views;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Reviews {
  /**
   * Id composto de chave estrangeiras.
   */
  @EmbeddedId
  @JsonView(Views.Public.class)
  private ReviewsId id;

  /** Conteúdo da review.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(length = 10000, name = "content", nullable = false)
  @JsonView({Views.Review.class})
  private String content;

  /** Campo indicando se a review foi editada ou não.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "edited", nullable = false)
  @JsonView({Views.Review.class})
  private Boolean edited = false;

  /** Campo indicando se o usuário recomenda ou não a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "recommended", nullable = false)
  @JsonView({Views.Review.class})
  private Boolean recommended;

  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  Reviews() { }

  /**
   * Constructor.
   * @param content Conteúdo da review feita pelo usuário
   * @param recommended Boolean indicando se o usuário recomenda ou não a media
   * @param media Media
   * @param user Usuário
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Reviews(
    final String content, final Boolean recommended,
    final Media media, final User user
  ) {
    this.content = content;
    this.recommended = recommended;
    this.id = new ReviewsId(user, media);
  }

  /**
   * Getter de content.
   * @return Retorna o conteúdo da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public String getContent() {
    return content;
  }

  /**
   * Setter de content.
   * @param contentString String com o comentário do usuário
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setContent(final String contentString) {
    this.content = contentString;
  }

  /**
   * Getter de edited.
   * @return Retorna um boolean dizendo se a review foi editada ou não
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Boolean getEdited() {
    return edited;
  }

  /**
   * Setter de edited.
   * @param editedBoolean Boolean
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setEdited(final Boolean editedBoolean) {
    this.edited = editedBoolean;
  }

  /**
   * Getter de id.
   * @return Retorna o Id da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public ReviewsId getId() {
    return id;
  }

  /**
   * Getter de mediaId.
   * @return Retorna a media da review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @JsonProperty("media")
  @JsonView({Views.ReviewPage.class})
  public Media getMediaId() {
    return this.id.getMediaId();
  }

  /**
   * Getter de userId.
   * @return Retorna o usuário que fez a review
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  @JsonView({Views.Review.class})
  @JsonProperty("user")
  public User getUserId() {
    return this.id.getUserId();
  }

  /**
   * Getter de recommended.
   * @return Retorna um boolean dizendo se o usuário recomenda a media ou não
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public Boolean getRecommended() {
    return recommended;
  }

  /**
   * Setter de recommended.
   * @param recommendedBoolean Boolean
   * @since 1.0
   * @author Victor Murilo
   * @version 1.0
   */
  public void setRecommended(final Boolean recommendedBoolean) {
    this.recommended = recommendedBoolean;
  }
}
