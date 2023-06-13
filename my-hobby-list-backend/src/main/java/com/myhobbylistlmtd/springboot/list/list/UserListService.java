package com.myhobbylistlmtd.springboot.list.list;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.interfaces.IBasicService;
import com.myhobbylistlmtd.springboot.list.status.ItemStatus;
import com.myhobbylistlmtd.springboot.list.status.ItemStatusRepository;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;

@Service
public class UserListService implements IBasicService<UserList, UserListId> {
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
  private ItemStatusRepository listItemTypeRepo;

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
  private ItemStatus findItemStatusByName(final String statusName)
  throws NotFoundException {
    ItemStatus status = listItemTypeRepo.findByStatusName(statusName);
    if (status == null) {
      throw new NotFoundException("Nome de status inválido");
    }
    return status;
  }

  /**
   * Insere um novo item na lista do usuário.
   * @param body Corpo da requisição
   * @return Uma string dizendo que deu bom.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String insertItemInList(final RequestUserListBody body) {
    User findUser = userService.findById(body.getUserId());
    Media findMedia = mediaService.findById(body.getMediaId());
    ItemStatus findStatus = findItemStatusByName(
      body.getStatus() == null ? "Em andamento" : body.getStatus()
    );

    UserListId id = new UserListId(findUser, findMedia);

    UserList list = new UserList();
    list.setId(id);
    list.setStatus(findStatus);
    list.setStatus(findStatus);
    list.setScore(body.getScore());
    list.setNotes(body.getNotes());
    list.setProgress(body.getProgress() == null ? 0 : body.getProgress());

    listRepo.save(list);

    return "Item inserido com sucesso";
  }

  /**
   * Encontra a lista de itens de um usuário.
   * @param userId Id do usuário a ser buscado
   * @return Uma lista de objetos UserList
   */
  public List<UserList> findListByUserId(final Long userId) {
    User user = userService.findById(userId);
    List<UserList> list = listRepo.findAllById_UserId(user);
    return list;
  }
}
