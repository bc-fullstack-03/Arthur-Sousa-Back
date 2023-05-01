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

import com.example.demo.model.Comentario;
import com.example.demo.server.ComentarioServer;

@RestController
@RequestMapping("comentario")
public class ComentarioControler {
  
  @Autowired
  ComentarioServer comentarioServer;

  @PostMapping("/criar")
  public ResponseEntity<Comentario> criar(@RequestBody Comentario comentario) {
    return comentarioServer.criar(comentario);
  }

  @GetMapping("/comentarios")
  public List<Comentario> comentarios(){
    return comentarioServer.comentarios();
  }

  @GetMapping("/{id}")
  public Comentario buscarPorId(@PathVariable UUID id){
    return comentarioServer.buscarPorId(id);
  }

  @DeleteMapping("/{id}")
  public void deleteComentario(@PathVariable UUID id){
    comentarioServer.excluirComentario(id);
  }

  @GetMapping("/count")
  public long countComentario(){
    return comentarioServer.countComentarios();
  }
}
