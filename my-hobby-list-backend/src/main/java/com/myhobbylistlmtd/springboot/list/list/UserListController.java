package com.myhobbylistlmtd.springboot.list.list;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseMessage;

@RestController
@RequestMapping("/list")
public class UserListController {
    /**
  * Serviço de list, responsável pelas regras de negócio.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserListService listService;

  /**
   * Rota de inserir um item na lista do usuário.
   * @param body Um objeto do tipo RequestUserListBody
   * @return Uma message dizendo que deu certo.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @PostMapping("/insert")
  ResponseMessage findRecentMedias(
    @RequestBody final RequestUserListBody body
  ) {
    String insertListItem = listService.insertItemInList(body);
    ResponseMessage response = new ResponseMessage();
    response.setMessage(insertListItem);
    return response;
  }

}
