package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.myhobbylistlmtd.springboot.exceptions.AlreadyTakenException;
import com.myhobbylistlmtd.springboot.exceptions.InvalidLoginException;
import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.image.ImageService;
import com.myhobbylistlmtd.springboot.objs.AllImagesUrl;
import com.myhobbylistlmtd.springboot.request.body.RequestRegisterUserBody;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;
import com.myhobbylistlmtd.springboot.user.UserService;
import com.myhobbylistlmtd.springboot.utils.Jwt;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
  private UserService userService;

  @Mock
  private UserRepository userRepo;

  @Mock
  private ImageService imageService;

  @Mock
  private Jwt jwt;

  @BeforeEach
  void setUp() {
    this.userService = new UserService(userRepo, imageService, this.jwt);
  }

  @Test
  public void findByIdWithSuccess() {
    Long id = Long.valueOf(1);
    Optional<User> user = Optional.of(new User("teste", "teste@gmail.com", "DAWHGDAUWGU"));
    
    when(userRepo.findById(id)).thenReturn(user);

    User serviceReturn = this.userService.findById(id);

    verify(userRepo).findById(id);
    assertThat(serviceReturn.getUsername(), is("teste"));
    assertThat(serviceReturn.getEmail(), is("teste@gmail.com"));
    assertThat(serviceReturn.getPassword(), is("DAWHGDAUWGU"));
  }

  @Test
  public void findByIdThrowsErrorWhenDoesntFoundUser() {
    Long id = Long.valueOf(1);
    when(userRepo.findById(id)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> this.userService.findById(id));

    verify(userRepo).findById(id);
  }

  @Test
  public void findByUsernameWithSuccess() {
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    
    when(userRepo.findByUsername("teste")).thenReturn(user);

    User serviceReturn = this.userService.findByUsername("teste");

    verify(userRepo).findByUsername("teste");
    assertThat(serviceReturn.getUsername(), is("teste"));
    assertThat(serviceReturn.getEmail(), is("teste@gmail.com"));
    assertThat(serviceReturn.getPassword(), is("DAWHGDAUWGU"));
  }

  @Test
  public void findByUsernameThrowsErrorWhenDoesntFoundUser() {
    when(userRepo.findByUsername("Teste")).thenReturn(null);

    assertThrows(NotFoundException.class, () -> this.userService.findByUsername("Teste"));

    verify(userRepo).findByUsername("Teste");
  }

  @Test
  public void findByEmailWithSuccess() {
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    
    when(userRepo.findByEmail("teste@gmail.com")).thenReturn(user);

    User serviceReturn = this.userService.findByEmail("teste@gmail.com");

    verify(userRepo).findByEmail("teste@gmail.com");
    assertThat(serviceReturn.getUsername(), is("teste"));
    assertThat(serviceReturn.getEmail(), is("teste@gmail.com"));
    assertThat(serviceReturn.getPassword(), is("DAWHGDAUWGU"));
  }

  @Test
  public void findByEmailThrowsErrorWhenDoesntFoundUser() {
    when(userRepo.findByEmail("teste@fiahfdwiuh.com")).thenReturn(null);

    assertThrows(NotFoundException.class, () -> this.userService.findByEmail("teste@fiahfdwiuh.com"));

    verify(userRepo).findByEmail("teste@fiahfdwiuh.com");
  }

  @Test
  public void validateLoginWithSuccess() {
    User user = new User("teste", "teste@gmail.com", "1234");
    Long id = Long.valueOf(2);
    when(this.jwt.generateJwtToken(id, "teste")).thenReturn("token");
    ReflectionTestUtils.setField(user, "id", id);

    when(userRepo.findByEmail("teste@gmail.com")).thenReturn(user);

    String serviceReturn = this.userService.validateLogin("teste@gmail.com", "1234");
    assertThat(serviceReturn, is("token"));

    verify(userRepo).findByEmail("teste@gmail.com");
    verify(jwt).generateJwtToken(Long.valueOf(2), "teste");
  }

  @Test
  public void validateLoginThrowsErrorWhenUserDoesntExists() {
    when(userRepo.findByEmail("teste@gmail.com")).thenReturn(null);

    assertThrows(InvalidLoginException.class, () -> this.userService.validateLogin("teste@gmail.com", "1234"));
    verify(userRepo).findByEmail("teste@gmail.com");
  }

  @Test
  public void validateLoginThrowsErrorWhenParamPasswordIsDifferentFromFindByEmailPassword() {
    User user = new User("teste", "teste@gmail.com", "1234");
    when(userRepo.findByEmail("teste@gmail.com")).thenReturn(user);

    assertThrows(InvalidLoginException.class, () -> this.userService.validateLogin("teste@gmail.com", "duawgdywag"));
    verify(userRepo).findByEmail("teste@gmail.com");
  }

  @Test
  public void registerUserWithSuccess() {
    User user = new User("teste", "teste@gmail.com", "1234");
    Long id = Long.valueOf(2);
    when(this.jwt.generateJwtToken(id, "teste")).thenReturn("token");
    ReflectionTestUtils.setField(user, "id", id);

    when(userRepo.save(any())).thenReturn(user);
    when(userRepo.findByUsernameOrEmail(anyString(), anyString())).thenReturn(new ArrayList<User>());

    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("teste@gmail.com");
    body.setPassword("1234");
    body.setUsername("teste");
    String serviceReturn = this.userService.registerUser(body);
    assertThat(serviceReturn, is("token"));

    verify(userRepo).save(any());
    verify(userRepo).findByUsernameOrEmail("teste", "teste@gmail.com");
    verify(jwt).generateJwtToken(Long.valueOf(2), "teste");
  }

  @Test
  public void registerUserThrowsErrorIfEmailIsAlreadyInUse() {
    User user = new User("FFFF", "teste@gmail.com", "1234");
    Long id = Long.valueOf(2);
    ReflectionTestUtils.setField(user, "id", id);

    List<User> usersList = new ArrayList<User>();
    usersList.add(user);
    usersList.add(new User("teste", "email@gmail.com", "1234"));

    when(userRepo.findByUsernameOrEmail(anyString(), anyString())).thenReturn(usersList);

    
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("teste@gmail.com");
    body.setPassword("1234");
    body.setUsername("teste");
    AlreadyTakenException exception = assertThrows(AlreadyTakenException.class, () -> this.userService.registerUser(body));
    
    assertThat(exception.getMessage(), is("O email teste@gmail.com já está em uso."));

    verify(userRepo).findByUsernameOrEmail("teste", "teste@gmail.com");
  }

  @Test
  public void registerUserThrowsErrorIfUsernameIsAlreadyInUse() {
    User user = new User("teste", "teste@gmail.com", "1234");
    Long id = Long.valueOf(2);
    ReflectionTestUtils.setField(user, "id", id);

    List<User> usersList = new ArrayList<User>();
    usersList.add(new User("t", "d", "1"));
    usersList.add(user);

    when(userRepo.findByUsernameOrEmail(anyString(), anyString())).thenReturn(usersList);

    
    RequestRegisterUserBody body = new RequestRegisterUserBody();
    body.setEmail("teste@teste.com");
    body.setPassword("1234");
    body.setUsername("teste");
    AlreadyTakenException exception = assertThrows(AlreadyTakenException.class, () -> this.userService.registerUser(body));

    assertThat(exception.getMessage(), is("O nome teste já está em uso."));

    verify(userRepo).findByUsernameOrEmail("teste", "teste@teste.com");
  }

  @Test
  public void changeProfileImageThrowsErrorIfImageDoesntExistsInBackend() {
    User user = new User("teste", "teste@gmail.com", "1234");
    Long id = Long.valueOf(2);
    ReflectionTestUtils.setField(user, "id", id);

    AllImagesUrl imagesUrlList = new AllImagesUrl();
    imagesUrlList.setImage("images/profile/default.png");
    imagesUrlList.setImage("images/profile/01.png");
    imagesUrlList.setImage("images/profile/02.png");
    imagesUrlList.setImage("images/profile/03.png");

    when(imageService.allImagesUrl(anyString(), anyString())).thenReturn(imagesUrlList);

    NotFoundException exception = assertThrows(NotFoundException.class, () -> this.userService.changeProfileImage(id, "blablablabla.jpg"));

    assertThat(exception.getMessage(), is("Imagem de perfil não encontrada"));

    verify(imageService).allImagesUrl("src/main/resources/images/profile", "images/profile");
  }

  @Test
  public void changeProfileImageWithSuccess() {
    Optional<User> user = Optional.of(new User("teste", "teste@gmail.com", "1234"));
    Long id = Long.valueOf(2);

    AllImagesUrl imagesUrlList = new AllImagesUrl();
    imagesUrlList.setImage("images/profile/default.png");
    imagesUrlList.setImage("images/profile/01.png");
    imagesUrlList.setImage("images/profile/02.png");
    imagesUrlList.setImage("images/profile/03.png");

    when(imageService.allImagesUrl(anyString(), anyString())).thenReturn(imagesUrlList);
    when(userRepo.findById(id)).thenReturn(user);
    when(userRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

    User serviceReturn = this.userService.changeProfileImage(id, "default.png");
    assertThat(serviceReturn.getProfileImage(), is("images/profile/default.png"));

    verify(imageService).allImagesUrl("src/main/resources/images/profile", "images/profile");
  }
}
