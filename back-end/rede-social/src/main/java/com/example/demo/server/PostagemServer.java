package com.example.demo.server;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.Postagem;
import com.example.demo.repository.PostagemRepository;

@Service
public class PostagemServer {

  @Autowired
  PostagemRepository postagemRepository;

  public ResponseEntity<Postagem> criar(Postagem postagem) {
    Postagem novoPostagem = postagemRepository.save(postagem);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoPostagem);
  }


  public List<Postagem> postagens() {
    return postagemRepository.findAll();
  }

  public Postagem buscarPorId(UUID id) {
    return postagemRepository.findById(id).orElse(null);
  }

  public long countPostagem() {
    return postagemRepository.count();
  }

  public void excluirPostagem(UUID id) {
    postagemRepository.deleteById(id);
  }

}
