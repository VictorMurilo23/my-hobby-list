package com.myhobbylistlmtd.springboot.media.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {
}
