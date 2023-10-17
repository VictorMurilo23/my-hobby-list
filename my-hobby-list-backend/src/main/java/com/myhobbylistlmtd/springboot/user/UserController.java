package com.myhobbylistlmtd.springboot.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.myhobbylistlmtd.springboot.request.body.RequestChangeProfileImage;
import com.myhobbylistlmtd.springboot.request.body.RequestLoginBody;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.response.body.ResponseLoginBody;
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
@RequestMapping("/user")
@Tag(name = "User")
public final class UserController {
  /**
  * Serviço do user, responsável pelas regras de negócio.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @Autowired
  private UserService service;

  /**
  * Função da rota de login, recebe a senha e email de um usuário e,
  caso de tudo certo, retorna um token.
  * @param body Recebe um body contendo a senha e o email do usuário.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @PostMapping("/login")
  @Operation(summary = "Login do usuário")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "O login é feito com sucesso e um JWT é retornado",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseLoginBody.class)) }
    )
  })
  ResponseLoginBody validateLogin(
    @Valid @RequestBody final RequestLoginBody body
  ) {
    String token = service.validateLogin(body.getEmail(), body.getPassword());
    ResponseLoginBody response = new ResponseLoginBody();
    response.setToken(token);
    return response;
  }

  /**
  * Função da rota de cadastro, recebe umaa senha, nome de usuário e email e,
  caso de tudo certo, retorna um token.
  * @param body Recebe um body contendo a senha, nome do usuário e o email.
  * @return Retorna um token caso uma exceção não seja lançada.
  * @since 1.0
  * @version 1.0
  * @author Victor Murilo
  */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Registrar um usuário")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "201",
      description = "O registro é feito com sucesso e um JWT é retornado",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = ResponseLoginBody.class)) }
    )
  })
  ResponseLoginBody registerUser(
    @Valid @RequestBody final RequestRegisterUserBody body
  ) {
    String token = service.registerUser(body);
    ResponseLoginBody response = new ResponseLoginBody();
    response.setToken(token);
    return response;
  }

  /**
   * Rota de pegar as informações de perfil.
   * @param username Nome do usuário
   * @return Um JSON contendo nome de usuário,
   data de registro, descrição e url da imagem de perfil
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Operation(summary = "Buscar perfil do usuário")
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna informações do usuário",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = User.class)) }
    )
  })
  @GetMapping("/profile/{username}")
  @ResponseStatus(HttpStatus.OK)
  @JsonView(Views.Public.class)
  User getProfile(final @PathVariable String username) {
    return service.findByUsername(username);
  }

  /**
   * Rota de mudar a imagem de perfil.
   * @param userId Id do usuário
   * @param body Corpo da requisição com a url da imagem
   * @return O objeto de User atualizado
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Operation(
    summary = "Mudar imagem de perfil",
    parameters = {
      @Parameter(
        in = ParameterIn.HEADER,
        schema = @Schema(implementation = String.class),
        name = "Authorization",
        required = true,
        description = "JWT gerado ao fazer login ou registro"
      )
    })
  @ApiResponses(value = {
    @ApiResponse(
      responseCode = "200",
      description = "Retorna informações do usuário",
      content = { @Content(mediaType = "application/json",
      schema = @Schema(implementation = User.class)) }
    )
  })
  @PatchMapping("/profile/change-profile-image")
  @ResponseStatus(HttpStatus.OK)
  @JsonView(Views.Public.class)
  User changeProfileImage(
    @RequestAttribute("userId") final Long userId,
    @Valid @RequestBody final RequestChangeProfileImage body
  ) {
    return service.changeProfileImage(userId, body.getImageUrl());
  }
}
