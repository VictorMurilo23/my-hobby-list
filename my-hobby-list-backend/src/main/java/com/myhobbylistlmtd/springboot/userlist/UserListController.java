package com.myhobbylistlmtd.springboot.userlist;

import org.springframework.http.HttpStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseMessage;
import com.myhobbylistlmtd.springboot.response.body.ResponseUserList;
import com.myhobbylistlmtd.springboot.utils.Views;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/list")
@Tag(name = "UserList")
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
   * @param userId userId setado no TokenInterceptor
   * @return Uma message dizendo que deu certo.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @PostMapping("/insert")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(
    summary = "Insere uma mídia na lista do usuário",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    }
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "Retorna uma mensagem dizendo que deu certo.",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseMessage.class)) }
    )
  })
  ResponseMessage insertItemInUserlist(
    @RequestBody @Valid final RequestUserListBody body,
    @RequestAttribute("userId") final Long userId
  ) {
    String insertListItem = listService.insertItemInList(body, userId);
    ResponseMessage response = new ResponseMessage();
    response.setMessage(insertListItem);
    return response;
  }

  /**
   * Rota de pegar os itens da lista do usuário.
   * @param username Nome do usuário utilizado na busca
   * @param statusName Query com o nome do status
   * @return Um objeto contendo os itens da lista do usuário
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Operation(
    summary = "Busca a lista de um usuário",
    description = "Busca todos os itens da lista do usuário. "
    + "É possível passar a query statusName caso queria só "
    + "itens de um status específico."
  )
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna um array com os itens da lista.",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseUserList.class)) }
    )
  })
  @GetMapping("/find/{username}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView(Views.Public.class)
  ResponseUserList findUserlistItems(
    final @PathVariable String username,
    final @RequestParam(required = false) String statusName
  ) {
    List<UserList> list;

    list = statusName == null ? listService.findList(username)
    : listService.findList(username, statusName);
    ResponseUserList response = new ResponseUserList();
    response.setList(list);
    return response;
  }
}
