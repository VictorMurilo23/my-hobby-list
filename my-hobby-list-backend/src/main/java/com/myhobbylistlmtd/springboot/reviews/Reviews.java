package com.myhobbylistlmtd.springboot.reviews;

import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Reviews {
  /** Id gerado automaticamente.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /** Conteúdo da review.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(length = 10000, name = "content", nullable = false)
  private String content;

  /** Campo indicando se a review foi editada ou não.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "edited", nullable = false)
  private Boolean edited = false;

  /** Campo indicando se o usuário recomenda ou não a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @Column(name = "recommended", nullable = false)
  private Boolean recommended;

  /**
  * Chave referente ao usuário.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;

  /**
  * Chave referente a media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  @ManyToOne
  @JoinColumn(name = "media_id")
  private Media mediaId;
  
  /** Default constructor.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  Reviews() {}

  public Reviews(String content, Boolean recommended, Media media, User user) {
    this.content = content;
    this.recommended = recommended;
    this.mediaId = media;
    this.userId = user;
  }
}
