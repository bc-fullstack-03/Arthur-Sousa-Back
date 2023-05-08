package com.example.demo.model;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


  
  @Component
  public class JwtUtil {
  
    @Value("${jwt.secret}")
    private String segredo;
  
    @Value("${jwt.expiration}")
    private Long expiracao;
  
    public String gerarToken(UUID uuid) {
  
      Date agora = new Date();
      Date expiracaoData = new Date(agora.getTime() + expiracao);
  
      return Jwts.builder()
          .claim("userId", uuid)
          .setIssuedAt(agora)
          .setExpiration(expiracaoData)
          .signWith(genSingInKey(),SignatureAlgorithm.HS256 )
          .compact();
  
    }

    public Key genSingInKey(){
      var bytes = Decoders.BASE64.decode(segredo);
      
      return Keys.hmacShaKeyFor(bytes);
    }
  
    public String getEmailFromToken(String token) {
  
      Claims claims = Jwts.parser()
          .setSigningKey(genSingInKey())
          .parseClaimsJws(token)
          .getBody();
  
      return claims.getSubject();
    }
  
    public String getUserIdFromToken(String token) {
      Claims claims = Jwts.parser()
          .setSigningKey(genSingInKey())
          .parseClaimsJws(token)
          .getBody();
  
      return (String) claims.get("userId");
    }
  
    public boolean validarToken(String token) {
      try {
          Jwts.parser().setSigningKey(genSingInKey()).parseClaimsJws(token);
          return true;
      } catch (JwtException | IllegalArgumentException e) {
          // Tratamento da exceção
          return false;
      }
  
    }
  
  }
