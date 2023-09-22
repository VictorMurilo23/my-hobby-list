package com.myhobbylistlmtd.springboot.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.myhobbylistlmtd.springboot.exceptions.BadRequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class Jwt {
    /**
   * key usada ao gerar um token.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  private SecretKey key;

  Jwt() {
    String secret = System.getenv("JWT_SECRET");
    this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  /**
   * Retorna um token contendo o id e username de um usuário.
   * @param userId Id que vai ser armazenado no token
   * @param username username que vai ser armazenado no token
   * @return Um token jwt
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String generateJwtToken(
    final Long userId, final String username
  ) {
    String jwt = Jwts.builder()
      .signWith(this.key, SignatureAlgorithm.HS256)
      .claim("username", username)
      .claim("id", userId)
      .compact();
    return jwt;
  }

  /**
   * Verifica o token e retorna o id do usuário.
   * @param token Uma string contendo um token jwt.
   * @return Uma string com o id do usuário.
   * @throws BadRequestException Ocorre quando o token é inválido.
   * @since 1.0
   * @version 1.0
   * @author Victor Murilo
   */
  public String verifyToken(
    final String token
  ) throws BadRequestException {
    try {
      Jws<Claims> jwt = Jwts.parserBuilder()
        .setSigningKey(this.key)
        .build()
        .parseClaimsJws(token);
      return jwt.getBody().get("id").toString();
    } catch (Exception e) {
      throw new BadRequestException("Token inválido");
    }
  }
}
