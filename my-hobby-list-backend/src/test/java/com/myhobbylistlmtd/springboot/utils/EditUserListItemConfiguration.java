package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatus;
import com.myhobbylistlmtd.springboot.listitemstatus.ListItemStatusRepository;
import com.myhobbylistlmtd.springboot.media.Media;
import com.myhobbylistlmtd.springboot.media.MediaRepository;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatus;
import com.myhobbylistlmtd.springboot.mediastatus.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.mediatype.MediaType;
import com.myhobbylistlmtd.springboot.mediatype.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.objs.MediaParams;
import com.myhobbylistlmtd.springboot.reviews.ReviewsRepository;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;
import com.myhobbylistlmtd.springboot.userlist.UserList;
import com.myhobbylistlmtd.springboot.userlist.UserListId;
import com.myhobbylistlmtd.springboot.userlist.UserListRepository;

@TestConfiguration
@Profile({ "test" })
public class EditUserListItemConfiguration {
  @Bean
  public CommandLineRunner runEditUserListItemConfiguration(UserRepository userRepo, MediaRepository mediaRepo,
      MediaStatusRepository statusRepo,
      MediaTypeRepository typeRepo,
      ReviewsRepository reviewRepo,
      ListItemStatusRepository listItemStatusRepository,
      UserListRepository listRepo) {
    MediaStatus mediaStatus = statusRepo.save(new MediaStatus("Completo"));
    User user = userRepo.save(
      new User("Victor", "email@gmail.com", "123")
    );
    MediaType mediaType = typeRepo.save(new MediaType("Jogo"));
    Media media = mediaRepo.save(new Media(
        new MediaParams("Tes1")
            .setLength(57)
            .setVolumes(3)
            .setStatus(mediaStatus)
            .setType(mediaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-29T08:01:00"))));
    ListItemStatus status = listItemStatusRepository.save(new ListItemStatus("Em andamento"));
    listItemStatusRepository.save(new ListItemStatus("Droppado"));
    UserListId id = new UserListId(user, media);
    
    UserList firstListItem = new UserList();
    firstListItem.setId(id);
    firstListItem.setStatus(status);
    firstListItem.setScore(10);
    firstListItem.setNotes("blablal");
    firstListItem.setProgress(5);
    listRepo.save(firstListItem);
    return null;
  }
}
