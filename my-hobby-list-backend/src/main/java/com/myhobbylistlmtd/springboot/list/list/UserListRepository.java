package com.myhobbylistlmtd.springboot.list.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserListRepository extends JpaRepository<
UserList, UserListId
> {
}
