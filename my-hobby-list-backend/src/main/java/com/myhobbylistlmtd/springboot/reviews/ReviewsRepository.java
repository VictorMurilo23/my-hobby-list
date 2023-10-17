package com.myhobbylistlmtd.springboot.reviews;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

  /**
   * Encontra uma review utilizando o userId e mediaId.
   * @param userId Id do usuário que fez a review
   * @param mediaId Id da media
   * @return Um objeto de Reviews
   */
  @Query(
    value = "SELECT * FROM reviews r WHERE r.user_id = ?1 AND r.media_id = ?2",
    nativeQuery = true
  )
  Reviews findReviewsByUserIdAndMediaId(Long userId, Long mediaId);

  /**
   * Encontra todas as reviews com base em uma id de uma media.
   * @param mediaId id da media
   * @param pageable página
   * @return Página
   */
  @Query(
    value = "SELECT * FROM reviews r WHERE r.media_id = ?1",
    nativeQuery = true
  )
  Page<Reviews> findAllByMediaId(Long mediaId, Pageable pageable);

  /**
   * Encontra todas as reviews feitas por só um usuário.
   * @param username nome do usuário
   * @param pageable página
   * @return Página
   */
  @Query(
    value = "SELECT re.*, us.username FROM reviews re LEFT "
    + "JOIN users us ON re.user_id = us.id WHERE us.username = ?1",
    nativeQuery = true
  )
  Page<Reviews> findAllUserReviews(String username, Pageable pageable);

  /**
   * Encontra uma review pelo nome do usuário e mediaId.
   * @param username Nome do usuário
   * @param mediaId Id da media
   * @return Retorna um objeto de Reviews com as informações da review
   */
  @Query(
    value = "SELECT re.* FROM reviews re LEFT JOIN users us ON re.user_id"
    + " = us.id WHERE us.username = ?1 AND re.media_id = ?2",
    nativeQuery = true
  )
  Reviews findReviewByUsernameAndMediaId(String username, Long mediaId);
}
