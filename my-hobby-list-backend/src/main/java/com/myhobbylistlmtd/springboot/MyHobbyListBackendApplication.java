package com.myhobbylistlmtd.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.myhobbylistlmtd.springboot.migrations.LoadMedia;

@SpringBootApplication
@Import({ LoadMedia.class })
public class MyHobbyListBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyHobbyListBackendApplication.class, args);
  }
}
