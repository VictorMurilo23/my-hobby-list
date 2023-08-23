package com.myhobbylistlmtd.springboot.utils;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.list.list.UserList;
import com.myhobbylistlmtd.springboot.list.list.UserListId;
import com.myhobbylistlmtd.springboot.list.list.UserListRepository;
import com.myhobbylistlmtd.springboot.list.status.ItemStatus;
import com.myhobbylistlmtd.springboot.list.status.ItemStatusRepository;
import com.myhobbylistlmtd.springboot.media.media.Media;
import com.myhobbylistlmtd.springboot.media.media.MediaRepository;
import com.myhobbylistlmtd.springboot.media.status.MediaStatus;
import com.myhobbylistlmtd.springboot.media.status.MediaStatusRepository;
import com.myhobbylistlmtd.springboot.media.type.MediaType;
import com.myhobbylistlmtd.springboot.media.type.MediaTypeRepository;
import com.myhobbylistlmtd.springboot.objs.MediaParams;
import com.myhobbylistlmtd.springboot.user.User;
import com.myhobbylistlmtd.springboot.user.UserRepository;

@TestConfiguration
@Profile({ "test" })
public class FindUserListConfiguration {
  @Bean
  public CommandLineRunner runFindUserListConfiguration(UserRepository userRepo, MediaRepository mediaRepo,
      MediaStatusRepository statusRepo,
      MediaTypeRepository typeRepo,
      ItemStatusRepository listItemStatusRepo,
      UserListRepository listRepo) {
    User user = userRepo.save(new User("Teste12345", "email@gmail.com", "123"));
    MediaStatus mediaStatus = statusRepo.save(new MediaStatus("Completo"));
    MediaType mediaType = typeRepo.save(new MediaType("Manga"));
    Media media1 = mediaRepo.save(new Media(
        new MediaParams("Tes1")
            .setLength(57)
            .setVolumes(3)
            .setStatus(mediaStatus)
            .setType(mediaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-29T08:01:00"))));
    Media media2 = mediaRepo.save(new Media(
        new MediaParams("Tes2")
            .setLength(33)
            .setVolumes(3)
            .setStatus(mediaStatus)
            .setType(mediaType)
            .setImageUrl("capa/capa")
            .setInsertionDate(
                LocalDateTime.parse("2023-05-29T08:01:00"))));
    
    ItemStatus onGoingItemStatus = listItemStatusRepo.save(new ItemStatus("Em andamento"));
    ItemStatus droppedItemStatus = listItemStatusRepo.save(new ItemStatus("Droppado"));

    UserList listItem1 = new UserList();
    listItem1.setId(new UserListId(user, media1));
    listItem1.setStatus(onGoingItemStatus);
    listItem1.setScore(10);
    listItem1.setNotes("Muito bom");
    listItem1.setProgress(12);

    UserList listItem2 = new UserList();
    listItem2.setId(new UserListId(user, media2));
    listItem2.setStatus(droppedItemStatus);
    listItem2.setScore(1);
    listItem2.setNotes("Pior coisa que eu já vi na minha vida");
    listItem2.setProgress(2);

    listRepo.save(listItem1);
    listRepo.save(listItem2);

    return null;
  }
}