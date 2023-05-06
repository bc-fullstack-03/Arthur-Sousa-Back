package com.example.demo.controlles;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Postagem;
import com.example.demo.server.PostagemServer;

@RestController
@RequestMapping("postagem")
public class PostagemControler {
  
  @Autowired
  PostagemServer postagemServer;

  @PostMapping("/criar")
  public ResponseEntity<Postagem> criar(@RequestBody Postagem postagem) {
    return postagemServer.criar(postagem);
  }

  @GetMapping("/postagens")
  public List<Postagem> postagens(){
    return postagemServer.postagens();
  }

  @GetMapping("/{id}")
  public Postagem buscarPorId(@PathVariable UUID id){
    return postagemServer.buscarPorId(id);
  }

  @DeleteMapping("/{id}")
  public void deletaPostagem(@PathVariable UUID id){
    postagemServer.excluirPostagem(id);
  }

  @GetMapping("/count")
  public long countPostag(){
    return postagemServer.countPostagem();
  }
}

