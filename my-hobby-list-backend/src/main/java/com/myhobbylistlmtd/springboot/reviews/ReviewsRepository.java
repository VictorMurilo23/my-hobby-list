package com.myhobbylistlmtd.springboot.reviews;

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
}
