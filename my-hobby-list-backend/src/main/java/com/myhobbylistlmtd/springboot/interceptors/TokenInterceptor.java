package com.myhobbylistlmtd.springboot.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.myhobbylistlmtd.springboot.utils.Jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    /**
   * Classe contendo os métodos de criar e verificar um token jwt.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  @Autowired
  private Jwt jwt;

  @Override
  public final boolean preHandle(
    final HttpServletRequest request,
    final HttpServletResponse response,
    final Object handler
  ) throws Exception {
    String token = request.getHeader("Authorization");
    String userId = jwt.verifyToken(token);
    request.setAttribute("userId", userId);
    return true;
  }
}
