package com.example.demo.server;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comentario;
import com.example.demo.repository.ComentarioRepository;

@Service
public class ComentarioServer {

  @Autowired
  ComentarioRepository comentarioRepository;

  public ResponseEntity<Comentario> criar(Comentario comentario) {
    Comentario c = comentarioRepository.save(comentario);
    return ResponseEntity.status(HttpStatus.CREATED).body(c);
  }

  public List<Comentario> comentarios(){
    return comentarioRepository.findAll();
  }

  public Comentario buscarPorId(UUID id){
    return comentarioRepository.findById(id).orElse(null);
  }

  public Long countComentarios(){
    return comentarioRepository.count();
  }

  public void excluirComentario(UUID id){
     comentarioRepository.deleteById(id);
  }
}
