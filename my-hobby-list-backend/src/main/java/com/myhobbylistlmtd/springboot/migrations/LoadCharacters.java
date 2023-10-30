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
    charsList.add(new Characters("Eikichi Onizuka", "images/characters/gto/onizuka.jpg"));
    charsList.add(new Characters("Azusa Fuyutsuki", "images/characters/gto/azusa-fuyutsuki.jpg"));
    charsList.add(new Characters("Hiroshi Uchiyamada", "images/characters/gto/hiroshi-uchiyamada.jpg"));
    charsList.add(new Characters("Miyabi Aizawa", "images/characters/gto/miyabi-aizawa.jpg"));
    charsList.add(new Characters("Ryuuji Danma", "images/characters/gto/ryuuji-danma.jpg"));
    charsList.add(new Characters("Tomoko Nomura", "images/characters/gto/tomoko-nomura.jpg"));
    charsList.add(new Characters("Urumi Kanzaki", "images/characters/gto/urumi-kanzaki.jpg"));
    charsList.add(new Characters("Yoshito Kikuchi", "images/characters/gto/yoshito-kikuchi.jpg"));

    charsList.add(new Characters("Cocoto", "images/characters/cocoto-fishing-master/cocoto.png"));

    charsList.add(new Characters("Shinji Ikari", "images/characters/evangelion/shinji.jpg"));
    charsList.add(new Characters("Gendou Ikari", "images/characters/evangelion/gendou.jpg"));
    charsList.add(new Characters("Kaworu Nagisa", "images/characters/evangelion/kaworu.jpg"));
    charsList.add(new Characters("Misato Katsuragi", "images/characters/evangelion/misato.jpg"));
    charsList.add(new Characters("Rei Ayanami", "images/characters/evangelion/rei.jpg"));
    charsList.add(new Characters("Ritsuko Akagi", "images/characters/evangelion/ritsuko.jpg"));
    charsList.add(new Characters("Ryouji Kaji", "images/characters/evangelion/ryouji-kaji.jpg"));
    charsList.add(new Characters("Asuka Langley Souryuu", "images/characters/evangelion/asuka.jpg"));

    charsList.add(new Characters("Jack", "images/characters/samurai-jack/samurai-jack.jpg"));
    charsList.add(new Characters("Aku", "images/characters/samurai-jack/aku.png"));

    charsList.add(new Characters("Delita Heiral", "images/characters/fft-twotl/delita.png"));
    charsList.add(new Characters("Alma Beoulve", "images/characters/fft-twotl/alma-beoulve.png"));
    charsList.add(new Characters("Ramza Beoulve", "images/characters/fft-twotl/ramza.png"));
    charsList.add(new Characters("Ovelia Atkascha", "images/characters/fft-twotl/ovelia.png"));

    charsList.add(new Characters("Hila", "images/characters/the-legendary-mechanic/hila.webp"));
    charsList.add(new Characters("Esper God", "images/characters/the-legendary-mechanic/esgod.jpg"));
    charsList.add(new Characters("Han Xiao", "images/characters/the-legendary-mechanic/han-xiao.jpg"));

    charsList.add(new Characters("Alfred", "images/characters/bloodborne/alfred.jpg"));
    charsList.add(new Characters("Boneca", "images/characters/bloodborne/boneca.png"));
    charsList.add(new Characters("O Caçador", "images/characters/bloodborne/cacador.jpg"));
    charsList.add(new Characters("Eileen O Corvo", "images/characters/bloodborne/eileen.png"));
    charsList.add(new Characters("Gehrman", "images/characters/bloodborne/gerhman.png"));

    charsList.add(new Characters("Hank Schrader", "images/characters/breaking-bad/hank-schrader.png"));
    charsList.add(new Characters("Jesse Pinkman", "images/characters/breaking-bad/jesse-pinkman.jpg"));
    charsList.add(new Characters("Marie Schrader", "images/characters/breaking-bad/marie.jpg"));
    charsList.add(new Characters("Skylar White", "images/characters/breaking-bad/skylar-white.png"));
    charsList.add(new Characters("Walter White", "images/characters/breaking-bad/walter-white.jpg"));

    charsList.add(new Characters("Ares", "images/characters/god-of-war/ares.png"));
    charsList.add(new Characters("Athena", "images/characters/god-of-war/athena.png"));
    charsList.add(new Characters("Kratos", "images/characters/god-of-war/kratos.jpg"));
    charsList.add(new Characters("Zeus", "images/characters/god-of-war/zeus.jpg"));
    charsList.add(new Characters("Spartano que morre", "images/characters/god-of-war/spartano.png"));

    charsList.add(new Characters("Okatsu", "images/characters/nioh/okatsu.jpg"));
    charsList.add(new Characters("Fuku", "images/characters/nioh/fuku.jpg"));
    charsList.add(new Characters("Hanzo Hattori", "images/characters/nioh/hanzo_hattori.png"));
    charsList.add(new Characters("Edward Kelley", "images/characters/nioh/edward-kelley.jpg"));
    charsList.add(new Characters("William Adams", "images/characters/nioh/william.png"));

    charsList.add(new Characters("Yuuji Endou", "images/characters/kaiji/endou.jpg"));
    charsList.add(new Characters("Kaiji Itou", "images/characters/kaiji/kaiji.jpg"));
    charsList.add(new Characters("Kazutaka Hyoudou", "images/characters/kaiji/kazutaka-hyoudou.jpg"));
    charsList.add(new Characters("Kouji Ishida", "images/characters/kaiji/kouji-ishida.jpg"));
    charsList.add(new Characters("Narrador", "images/characters/kaiji/narrador.jpg"));
    charsList.add(new Characters("Makoto Sahara", "images/characters/kaiji/sahara.jpg"));
    charsList.add(new Characters("Yukio Tonegawa", "images/characters/kaiji/tonegawa.jpg"));

    repository.saveAll(charsList);
    return null;
  }
}
