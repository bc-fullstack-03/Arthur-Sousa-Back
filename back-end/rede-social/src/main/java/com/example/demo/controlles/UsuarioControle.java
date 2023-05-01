package com.example.demo.controlles;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Usuario;
import com.example.demo.server.UsuarioServer;

@RestController
@RequestMapping("usuario")
public class UsuarioControle {

  @Autowired
  UsuarioServer usuarioServer;

  @PostMapping("/criar")
  public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
    return usuarioServer.criar(usuario);
  }

  @GetMapping("/usuarios")
  public List<Usuario> usuarios() {
    return usuarioServer.usuarios();
  }

  @GetMapping("/{id}")
  public Usuario buscarPorId(@PathVariable UUID id) {
    return usuarioServer.buscarPorId(id);
  }

  @GetMapping("/count")
  public long countUsuarios() {
    return usuarioServer.countUsuarios();
  }

  @DeleteMapping("/{id}")
  public void deleteUsuario(@PathVariable UUID id) {
    usuarioServer.excluirUsuarios(id);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Usuario> atualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuario) {
    return usuarioServer.atualizarUsuario(id,usuario);
  }

}

