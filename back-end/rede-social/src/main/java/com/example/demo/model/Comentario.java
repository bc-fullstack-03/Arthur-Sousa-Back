
package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "comentarios")
public class Comentario {

 
  private UUID id = UUID.randomUUID();

  private String texto;
  private LocalDateTime dataCriaco;

  @JsonSerialize(using = ToStringSerializer.class)
  private UUID idUsuario;

  @JsonSerialize(using = ToStringSerializer.class)
  private UUID idPost;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTexto() {
    return texto;
  }

  public void setTexto(String texto) {
    this.texto = texto;
  }

  public LocalDateTime getDataCriaco() {
    return dataCriaco;
  }

  public void setDataCriaco(LocalDateTime dataCriaco) {
    this.dataCriaco = dataCriaco;
  }

  public UUID getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(UUID idUsuario) {
    this.idUsuario = idUsuario;
  }

  public UUID getIdPost() {
    return idPost;
  }

  public void setIdPost(UUID idPost) {
    this.idPost = idPost;
  }
  
}