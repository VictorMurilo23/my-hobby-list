package com.myhobbylistlmtd.springboot.reviewcomments;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewCommentsRepository extends JpaRepository<
  ReviewComments,
  ReviewCommentsId
> {

  /**
   * Encontra todos os comentários feitos em uma review.
   * @param username Nome do usuário que fez a review
   * @param mediaId Id da media
   * @return Lista com todos os comentários
   */
  @Query(
    value = "SELECT * FROM review_comments rc LEFT JOIN users us"
    + " ON rc.review_id_user_id = us.id WHERE us.username = ?1"
    + " AND rc.review_id_media_id = ?2 ORDER BY rc.insertion_date ASC",
    nativeQuery = true
  )
  List<ReviewComments> findReviewsByUsernameAndMediaId(
    String username, Long mediaId
  );
}
