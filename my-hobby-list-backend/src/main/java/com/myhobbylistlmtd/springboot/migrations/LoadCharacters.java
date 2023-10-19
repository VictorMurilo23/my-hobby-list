package com.myhobbylistlmtd.springboot.migrations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.myhobbylistlmtd.springboot.characters.CharacterRepository;
import com.myhobbylistlmtd.springboot.characters.Characters;

@Configuration
@Profile({ "production" })
public class LoadCharacters {
  /**
   * Salvar os tipos de status que itens da lista podem ter.
   * @param repository Repositório de ItemStatus
   * @return null
   */
  @Bean
  public CommandLineRunner runLoadCharacters(
      final CharacterRepository repository) {
    List<Characters> charsList = new ArrayList<Characters>();
    charsList.add(new Characters("Eikichi Onizuka", "/characters/gto/onizuka.jpg"));
    charsList.add(new Characters("Azusa Fuyutsuki", "/characters/gto/azusa-fuyutsuki.jpg"));
    charsList.add(new Characters("Hiroshi Uchiyamada", "/characters/gto/hiroshi-uchiyamada.jpg"));
    charsList.add(new Characters("Miyabi Aizawa", "/characters/gto/miyabi-aizawa.jpg"));
    charsList.add(new Characters("Ryuuji Danma", "/characters/gto/ryuuji-danma.jpg"));
    charsList.add(new Characters("Tomoko Nomura", "/characters/gto/tomoko-nomura.jpg"));
    charsList.add(new Characters("Urumi Kanzaki", "/characters/gto/urumi-kanzaki.jpg"));
    charsList.add(new Characters("Yoshito Kikuchi", "/characters/gto/yoshito-kikuchi.jpg"));

    charsList.add(new Characters("Cocoto", "/characters/cocoto-fishing-master/cocoto.png"));

    charsList.add(new Characters("Shinji Ikari", "/characters/evangelion/shinji.jpg"));
    charsList.add(new Characters("Gendou Ikari", "/characters/evangelion/gendou.jpg"));
    charsList.add(new Characters("Kaworu Nagisa", "/characters/evangelion/kaworu.jpg"));
    charsList.add(new Characters("Misato Katsuragi", "/characters/evangelion/misato.jpg"));
    charsList.add(new Characters("Rei Ayanami", "/characters/evangelion/rei.jpg"));
    charsList.add(new Characters("Ritsuko Akagi", "/characters/evangelion/ritsuko.jpg"));
    charsList.add(new Characters("Ryouji Kaji", "/characters/evangelion/ryouji-kaji.jpg"));
    charsList.add(new Characters("Asuka Langley Souryuu", "/characters/evangelion/asuka.jpg"));

    charsList.add(new Characters("Jack", "/characters/samurai-jack/samurai-jack.jpg"));
    charsList.add(new Characters("Aku", "/characters/samurai-jack/aku.png"));

    charsList.add(new Characters("Delita Heiral", "/characters/fft-twotl/delita.png"));
    charsList.add(new Characters("Alma Beoulve", "/characters/fft-twotl/alma-beoulve.png"));
    charsList.add(new Characters("Ramza Beoulve", "/characters/fft-twotl/ramza.png"));
    charsList.add(new Characters("Ovelia Atkascha", "/characters/fft-twotl/ovelia.png"));

    charsList.add(new Characters("Hila", "/characters/the-legendary-mechanic/hila.webp"));
    charsList.add(new Characters("Esper God", "/characters/the-legendary-mechanic/esgod.jpg"));
    charsList.add(new Characters("Han Xiao", "/characters/the-legendary-mechanic/han-xiao.jpg"));

    charsList.add(new Characters("Alfred", "/characters/bloodborne/alfred.jpg"));
    charsList.add(new Characters("Boneca", "/characters/bloodborne/boneca.png"));
    charsList.add(new Characters("O Caçador", "/characters/bloodborne/cacador.jpg"));
    charsList.add(new Characters("Eileen O Corvo", "/characters/bloodborne/eileen.png"));
    charsList.add(new Characters("Gehrman", "/characters/bloodborne/gerhman.png"));

    charsList.add(new Characters("Hank Schrader", "/characters/breaking-bad/hank-schrader.png"));
    charsList.add(new Characters("Jesse Pinkman", "/characters/breaking-bad/jesse-pinkman.jpg"));
    charsList.add(new Characters("Marie Schrader", "/characters/breaking-bad/marie.jpg"));
    charsList.add(new Characters("Skylar White", "/characters/breaking-bad/skylar-white.png"));
    charsList.add(new Characters("Walter White", "/characters/breaking-bad/walter-white.jpg"));

    charsList.add(new Characters("Ares", "/characters/god-of-war/ares.png"));
    charsList.add(new Characters("Athena", "/characters/god-of-war/athena.png"));
    charsList.add(new Characters("Kratos", "/characters/god-of-war/kratos.jpg"));
    charsList.add(new Characters("Zeus", "/characters/god-of-war/zeus.jpg"));
    charsList.add(new Characters("Spartano que morre", "/characters/god-of-war/spartano.png"));

    repository.saveAll(charsList);
    return null;
  }
}
