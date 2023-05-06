package com.example.demo.model;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "likes")
public class Like {

  private UUID idUsuario;

  public UUID getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(UUID idUsuario) {
    this.idUsuario = idUsuario;
  }
  
  
}