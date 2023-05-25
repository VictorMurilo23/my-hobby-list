package com.myhobbylistlmtd.springboot.request.body;

public class RequestMediaBody {
  /** Atributo com o nome da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  private String name;

  /** Getter de name.
  * @return Nome da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public String getName() {
    return name;
  }

  /** Setter de name.
  * @param newName Uma string com o novo nome da media.
  * @since 1.0
  * @author Victor Murilo
  * @version 1.0
  */
  public void setName(final String newName) {
    this.name = newName;
  }
}
