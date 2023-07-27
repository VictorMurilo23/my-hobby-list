package com.myhobbylistlmtd.springboot.list.list;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myhobbylistlmtd.springboot.user.User;

@Repository
public interface UserListRepository extends JpaRepository<
UserList, UserListId
> {
  /**
   * Encontra os itens da lista do usuário.
   * @param user Objeto User com um id válido.
   * @return Uma lista contendo os itens de userlist.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  // CHECKSTYLE:OFF: MethodNameCheck
  List<UserList> findAllById_UserId(User user);

  /**
   * Encontra os itens da lista do usuário com base no username e statusName.
   * @param username Nome do usuário utilizado na busca
   * @param statusName Nome do status utilizado na busca
   * @return Uma lista contendo os itens de userlist
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  List<UserList> findAllById_UserId_UsernameAndStatus_StatusName(
    String username, String statusName
  );
}
