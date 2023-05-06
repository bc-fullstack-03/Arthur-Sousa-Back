package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AdicionarAmigo;

@Repository
public interface AdiconarAmigoRepository extends MongoRepository<AdicionarAmigo, UUID>{
  Optional<AdicionarAmigo> findByUserIdAndAmigoId(UUID userId, UUID amigoId);
  List<AdicionarAmigo> findByUserId(UUID userId);

}
