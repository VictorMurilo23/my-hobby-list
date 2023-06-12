package com.myhobbylistlmtd.springboot.list.list;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myhobbylistlmtd.springboot.request.body.RequestUserId;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseMessage;
import com.myhobbylistlmtd.springboot.response.body.ResponseUserList;

import jakarta.validation.Valid;

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
  @ResponseStatus(HttpStatus.CREATED)
  ResponseMessage insertItemInUserlist(
    @RequestBody @Valid final RequestUserListBody body
  ) {
    String insertListItem = listService.insertItemInList(body);
    ResponseMessage response = new ResponseMessage();
    response.setMessage(insertListItem);
    return response;
  }

  /**
   * Rota de pegar os itens da lista do usuário.
   * @param body Um objeto de RequestUserId
   * @return Um objeto contendo os itens da lista do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @GetMapping("/find")
  @ResponseStatus(HttpStatus.OK)
  ResponseUserList findUserlistItems(
    @RequestBody @Valid final RequestUserId body
  ) {
    List<UserList> list = listService.findListByUserId(body.getUserId());
    ResponseUserList response = new ResponseUserList();
    response.setList(list);
    return response;
  }
}
