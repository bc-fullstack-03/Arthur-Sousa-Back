package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Document(collection = "postagens")
public class Postagem {

  private UUID id = UUID.randomUUID();

  private String titulo;
  private String conteudo;

  @JsonSerialize(using = ToStringSerializer.class)
  private UUID usuarioId;
  private LocalDateTime dataDaPublicacao;

  private List<Comentario> comentarios = new ArrayList<Comentario>();
  private List<Like> likes = new ArrayList<Like>();

 


  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getConteudo() {
    return conteudo;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
  }

  public LocalDateTime getDataDaPublicacao() {
    return dataDaPublicacao;
  }

  public void setDataDaPublicacao(LocalDateTime dataDaPublicacao) {
    this.dataDaPublicacao = dataDaPublicacao;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public UUID getUsuarioId() {
    return usuarioId;
  }

  public void setUsuarioId(UUID usuarioId) {
    this.usuarioId = usuarioId;
  }
  public List<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }

  public List<Like> getLikes() {
    return likes;
  }

  public void setLikes(List<Like> likes) {
    this.likes = likes;
  }

  
}
