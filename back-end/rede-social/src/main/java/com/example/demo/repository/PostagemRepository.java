package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Postagem;

@Repository
public interface PostagemRepository extends MongoRepository<Postagem, UUID> {

}
