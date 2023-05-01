package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comentario;

@Repository
public interface ComentarioRepository extends MongoRepository<Comentario , UUID> {
  
}
