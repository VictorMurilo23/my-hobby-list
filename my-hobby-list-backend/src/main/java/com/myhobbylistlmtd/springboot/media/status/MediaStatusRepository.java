package com.myhobbylistlmtd.springboot.media.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaStatusRepository extends JpaRepository<
  MediaStatus, Long
> {
}