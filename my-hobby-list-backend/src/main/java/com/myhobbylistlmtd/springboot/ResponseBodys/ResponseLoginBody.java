package com.myhobbylistlmtd.springboot.ResponseBodys;

public class ResponseLoginBody {
  public String token;

  public ResponseLoginBody(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
