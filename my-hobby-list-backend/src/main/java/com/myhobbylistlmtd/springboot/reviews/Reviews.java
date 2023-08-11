package com.myhobbylistlmtd.springboot.reviews;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
