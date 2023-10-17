package com.myhobbylistlmtd.springboot.reviewcomments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewCommentsRepository extends JpaRepository<
  ReviewComments,
  ReviewCommentsId
> {

}
