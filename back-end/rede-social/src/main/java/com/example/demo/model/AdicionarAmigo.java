package com.example.demo.model;

import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AdicionarAmigo {
  
  private UUID userId;
  private UUID amigoId;

  
  public AdicionarAmigo(UUID userId, UUID amigoId) {
    this.userId = userId;
    this.amigoId = amigoId;
  }
  
  public UUID getUserId() {
    return userId;
  }
  public void setUserId(UUID userId) {  
    this.userId = userId;
  }
  public UUID getAmigoId() {
    return amigoId;
  }
  public void setAmigoId(UUID amigoId) {
    this.amigoId = amigoId;
  }

  
}
