package com.myhobbylistlmtd.springboot.list.list;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.interfaces.FindById;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatus;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatusRepository;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@Service
public class UserListService implements FindById<UserList, UserListId> {
    /**
  * Repositório utilizado para fazer as interações com o banco de dados.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserListRepository listRepo;

  /**
  * Repositório utilizado para buscar tipos de status de lista.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private ListItemStatusRepository listItemTypeRepo;

  /**
   * Service relacionado aos usuários.
   * @since 1.0
   * @version 1.9
   * @author Victor Murilo
   */
  @Autowired
  private UserService userService;

  /**
   * Service relacionado as medias.
   * @since 1.0
   * @version 1.9
   * @author Victor Murilo
   */
  @Autowired
  private MediaService mediaService;

  /**
   * Constructor utilizado nos testes unitários.
   * @param mediaService Mock de MediaService
   * @param userService Mock de UserService
   * @param listItemTypeRepo Mock de ItemStatusRepository
   * @param listRepo Mock de UserListRepository
   * @since 1.0
   * @version 1.9
   * @author Victor Murilo
   */
  public UserListService(
    final MediaService mediaService,
    final UserService userService,
    final ListItemStatusRepository listItemTypeRepo,
    final UserListRepository listRepo
  ) {
    this.mediaService = mediaService;
    this.userService = userService;
    this.listItemTypeRepo = listItemTypeRepo;
    this.listRepo = listRepo;
  }

  @Override
  public final UserList findById(final UserListId id) throws NotFoundException {
    try {
      UserList list = listRepo.findById(id).get();
      return list;
    } catch (NoSuchElementException e) {
      throw new NotFoundException("Lista não encontrada!");
    }
  }

  /**
   * Encontra no banco de dados um status.
   * @param statusName Nome do status a ser buscado.
   * @return Um objeto de tipo ItemStatus contendo
   as informações do status encontrado
   * @throws NotFoundException Ocorre quando o status não é encontrado.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private ListItemStatus findItemStatusByName(final String statusName)
  throws NotFoundException {
    ListItemStatus status = listItemTypeRepo.findByStatusName(statusName);
    if (status == null) {
      throw new NotFoundException("Nome de status inválido");
    }
    return status;
  }

  /**
   * Insere um novo item na lista do usuário.
   * @param body Corpo da requisição
   * @param userId Usado para buscar se o usuário realmente existe
   * @return Uma string dizendo que deu bom.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String insertItemInList(
    final RequestUserListBody body, final Long userId
  ) {
    User findUser = userService.findById(userId);
    Media findMedia = mediaService.findById(body.getMediaId());
    ListItemStatus findStatus = findItemStatusByName(
      body.getStatus() == null ? "Em andamento" : body.getStatus()
    );

    UserListId id = new UserListId(findUser, findMedia);

    UserList list = new UserList();
    list.setId(id);
    list.setStatus(findStatus);
    list.setScore(body.getScore());
    list.setNotes(body.getNotes());
    list.setProgress(body.getProgress() == null ? 0 : body.getProgress());

    listRepo.save(list);

    return "Item inserido com sucesso";
  }

  /**
   * Encontra a lista do usuário por nome de usuário.
   * @param username Nome do usuário utilizado na busca
   * @return Uma lista com objetos UserList
   * @version 2.0
   * @since 1.0
   * @author Victor Murilo
   */
  public List<UserList> findList(final String username) {
    User user = userService.findByUsername(username);
    List<UserList> list = listRepo.findAllById_UserId(user);
    return list;
  }

  /**
   * Encontra a lista do usuário por nome de usuário e nome de status.
   * @param username Nome do usuário utilizado na busca
   * @param statusName Nome do status utilizado na busca
   * @return Uma lista com objetos UserList
   * @version 1.0
   * @since 1.0
   * @author Victor Murilo
   */
  public List<UserList> findList(
    final String username, final String statusName
  ) {
    User user = userService.findByUsername(username);
    ListItemStatus status = this.findItemStatusByName(statusName);
    List<UserList> list = listRepo
    .findAllById_UserId_UsernameAndStatus_StatusName(
      user.getUsername(), status.getStatusName()
    );
    return list;
  }
}
