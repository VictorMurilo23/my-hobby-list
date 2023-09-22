package com.myhobbylistlmtd.springboot.unit;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.myhobbylistlmtd.springboot.exceptions.NotFoundException;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatus;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatusRepository;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaService;
import com.myhobbylistlmtd.springboot.objs.MediaParams;
import com.myhobbylistlmtd.springboot.request.body.RequestUserListBody;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserService;
import com.myhobbylistlmtd.springboot.userlist.UserList;
import com.myhobbylistlmtd.springboot.userlist.UserListId;
import com.myhobbylistlmtd.springboot.userlist.UserListRepository;
import com.myhobbylistlmtd.springboot.userlist.UserListService;

@ExtendWith(MockitoExtension.class)
public class UserListServiceTest {
  private UserListService listService;

  @Mock
  private MediaService mediaService;
  
  @Mock
  private UserService userService = mock(UserService.class);
  
  @Mock
  private ListItemStatusRepository listItemTypeRepo;
  
  @Mock
  private UserListRepository listRepo;

  @BeforeEach
  void setUp() {
    this.listService = new UserListService(mediaService, userService, listItemTypeRepo, listRepo);
  }

  @Test
  public void findByIdWithSucess() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    UserListId id = new UserListId(user, media);
    ListItemStatus status = new ListItemStatus("Em andamento");

    UserList list = new UserList();
    list.setId(id);
    list.setStatus(status);
    list.setScore(10);
    list.setNotes("blablal");
    list.setProgress(5);
    
    when(listRepo.findById(id)).thenReturn(Optional.of(list));

    UserList serviceReturn = this.listService.findById(id);

    verify(listRepo).findById(id);
    assertThat(serviceReturn.getNotes(), is("blablal"));
    assertThat(serviceReturn.getScore(), is(10));
    assertThat(serviceReturn.getStatus().getStatusName(), is("Em andamento"));
    assertThat(serviceReturn.getProgress(), is(5));
    assertThat(serviceReturn.getId(), is(id));
  }

  @Test()
  public void findByIdThrowsError() {
    when(listRepo.findById(any())).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> this.listService.findById(any()));

    verify(listRepo).findById(any());
  }

  @Test
  public void insertItemInListWithSuccess() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    ListItemStatus status = new ListItemStatus("Em andamento");
    Long mediaId = Long.valueOf(10);
    Long userId = Long.valueOf(2);

    when(userService.findById(anyLong())).thenReturn(user);
    when(mediaService.findById(anyLong())).thenReturn(media);
    when(listItemTypeRepo.findByStatusName(anyString())).thenReturn(status);
    when(listRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(mediaId);
    body.setNotes("dfdadwad");
    body.setProgress(16);
    body.setScore(6);
    body.setStatus("Concluído");
    String serviceReturn = this.listService.insertItemInList(body, userId);

    verify(listRepo).save(any());
    verify(userService).findById(userId);
    verify(mediaService).findById(mediaId);
    verify(listItemTypeRepo).findByStatusName("Concluído");
    assertThat(serviceReturn, is("Item inserido com sucesso"));
  }

  @Test
  public void insertItemInListThrowsErrorStatusNameDoesntExists() {
    Long userId = Long.valueOf(2);
    Long mediaId = Long.valueOf(10);

    when(listItemTypeRepo.findByStatusName(anyString())).thenReturn(null);

    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(mediaId);
    body.setNotes("dfdadwad");
    body.setProgress(16);
    body.setScore(6);
    body.setStatus("Não existe");
    assertThrows(NotFoundException.class, () -> this.listService.insertItemInList(body, userId));

    verify(listItemTypeRepo).findByStatusName("Não existe");
  }

  @Test
  public void insertItemInListShouldChangeStatusIfStatusInBodyIsNull() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    ListItemStatus status = new ListItemStatus("Em andamento");
    Long mediaId = Long.valueOf(10);
    Long userId = Long.valueOf(2);

    when(userService.findById(anyLong())).thenReturn(user);
    when(mediaService.findById(anyLong())).thenReturn(media);
    when(listItemTypeRepo.findByStatusName(anyString())).thenReturn(status);
    when(listRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(mediaId);
    body.setNotes("dfdadwad");
    body.setProgress(16);
    body.setScore(6);
    
    this.listService.insertItemInList(body, userId);
    
    verify(listItemTypeRepo).findByStatusName("Em andamento");
  }

  @Test
  public void insertItemInListShouldChangeProgressIfProgressInBodyIsNull() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    ListItemStatus status = new ListItemStatus("Em andamento");
    Long mediaId = Long.valueOf(10);
    Long userId = Long.valueOf(2);

    when(userService.findById(anyLong())).thenReturn(user);
    when(mediaService.findById(anyLong())).thenReturn(media);
    when(listItemTypeRepo.findByStatusName(anyString())).thenReturn(status);
    when(listRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

    RequestUserListBody body = new RequestUserListBody();
    body.setMediaId(mediaId);
    body.setNotes("dfdadwad");
    body.setScore(6);
    body.setStatus("Não existe");
    this.listService.insertItemInList(body, userId);
    // TODO arrumar um jeito de verificar se o setProgress de UserList é chamado com o parâmetro 0
    verify(listRepo).save(any());
  }

  @Test
  public void findList() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    ListItemStatus status = new ListItemStatus("Em andamento");
    UserListId id = new UserListId(user, media);
    
    UserList firstListItem = new UserList();
    firstListItem.setId(id);
    firstListItem.setStatus(status);
    firstListItem.setScore(10);
    firstListItem.setNotes("blablal");
    firstListItem.setProgress(5);
    
    UserList secondListItem = new UserList();
    secondListItem.setId(new UserListId(user, new Media(new MediaParams("Teste2"))));
    secondListItem.setStatus(status);
    secondListItem.setScore(5);
    secondListItem.setNotes("2021313");
    secondListItem.setProgress(65);

    List<UserList> items = Arrays.asList(firstListItem, secondListItem);

    when(userService.findByUsername(anyString())).thenReturn(user);
    when(listRepo.findAllById_UserId(any())).thenReturn(items);

    List<UserList> serviceReturn = this.listService.findList("teste");

    verify(userService).findByUsername("teste");
    verify(listRepo).findAllById_UserId(any());

    assertThat(serviceReturn.size(), is(2));
    assertThat(serviceReturn.get(0).getProgress(), is(5));
    assertThat(serviceReturn.get(0).getNotes(), is("blablal"));
    assertThat(serviceReturn.get(0).getScore(), is(10));
    assertThat(serviceReturn.get(0).getStatus().getStatusName(), is("Em andamento"));
    assertThat(serviceReturn.get(0).getId().getUserId().getUsername(), is("teste"));
    assertThat(serviceReturn.get(0).getId().getMediaId().getName(), is("Teste1"));
    
    assertThat(serviceReturn.get(1).getProgress(), is(65));
    assertThat(serviceReturn.get(1).getNotes(), is("2021313"));
    assertThat(serviceReturn.get(1).getScore(), is(5));
    assertThat(serviceReturn.get(1).getStatus().getStatusName(), is("Em andamento"));
    assertThat(serviceReturn.get(1).getId().getUserId().getUsername(), is("teste"));
    assertThat(serviceReturn.get(1).getId().getMediaId().getName(), is("Teste2"));
  }

  @Test
  public void findListWithStatusName() {
    Media media = new Media(new MediaParams("Teste1"));
    User user = new User("teste", "teste@gmail.com", "DAWHGDAUWGU");
    ListItemStatus status = new ListItemStatus("Em andamento");
    UserListId id = new UserListId(user, media);
    
    UserList firstListItem = new UserList();
    firstListItem.setId(id);
    firstListItem.setStatus(status);
    firstListItem.setScore(10);
    firstListItem.setNotes("blablal");
    firstListItem.setProgress(5);
    List<UserList> items = Arrays.asList(firstListItem);

    when(userService.findByUsername(anyString())).thenReturn(user);
    when(listRepo.findAllById_UserId_UsernameAndStatus_StatusName(anyString(), anyString())).thenReturn(items);
    when(listItemTypeRepo.findByStatusName(anyString())).thenReturn(status);

    List<UserList> serviceReturn = this.listService.findList("teste", "Em andamento");

    verify(userService).findByUsername("teste");
    verify(listRepo).findAllById_UserId_UsernameAndStatus_StatusName(anyString(), anyString());

    assertThat(serviceReturn.size(), is(1));
    assertThat(serviceReturn.get(0).getProgress(), is(5));
    assertThat(serviceReturn.get(0).getNotes(), is("blablal"));
    assertThat(serviceReturn.get(0).getScore(), is(10));
    assertThat(serviceReturn.get(0).getStatus().getStatusName(), is("Em andamento"));
    assertThat(serviceReturn.get(0).getId().getUserId().getUsername(), is("teste"));
    assertThat(serviceReturn.get(0).getId().getMediaId().getName(), is("Teste1"));
  }
}
