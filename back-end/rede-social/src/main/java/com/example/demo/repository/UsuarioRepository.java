package com.example.demo.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, UUID> {
  Usuario findByEmailAndSenha(String email, String senha);
  Usuario findByEmail(String email);
 

}
