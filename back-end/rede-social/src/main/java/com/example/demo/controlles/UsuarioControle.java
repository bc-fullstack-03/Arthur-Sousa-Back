package com.example.demo.controlles;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Usuario;
import com.example.demo.server.AdicionarAmigoServer;
import com.example.demo.server.UsuarioServer;

@RestController
@RequestMapping("usuario")
@CrossOrigin(origins = "*")
public class UsuarioControle {

  @Autowired
  UsuarioServer usuarioServer;

  @Autowired
  private AdicionarAmigoServer adicionarAmigoServer;

  @PostMapping("/criar")
  public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario) {
    try {
      return usuarioServer.criar(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
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
    return usuarioServer.atualizarUsuario(id, usuario);
  }

  @PostMapping("/{userId}/amigos")
  public ResponseEntity<String> adicionarAmigos(@PathVariable UUID userId, @RequestParam UUID friendId) {
    try {
      adicionarAmigoServer.adicionar(userId, friendId);
      return ResponseEntity.ok("Amigo adicionado com sucesso.");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @GetMapping("/{userId}/amigos")
  public List<Usuario> buscarAmigos(@PathVariable UUID userId) {
    return adicionarAmigoServer.listaDeAmigos(userId);
  }

  @PostMapping("/login")
  public ResponseEntity<String> loginUsuario(@RequestBody Usuario user) throws Exception {
    String token = usuarioServer.autenticar(user.getEmail(), user.getSenha());
    return ResponseEntity.ok(token);
  }

  @GetMapping("/validarToken")
  public ResponseEntity<Usuario> getUsuarioFromToken(@RequestHeader("Authorization") String authorizationHeader) {

    if (authorizationHeader == null || authorizationHeader.length() <= 7) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    String token = authorizationHeader.substring(7); // Remove a palavra "Bearer" do header
    try {
      Usuario usuario = usuarioServer.getUserFromToken(token);
      return ResponseEntity.ok(usuario);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @PostMapping("/{userId}/imagem")
  public ResponseEntity<?> uploadImage(@PathVariable UUID userId, @RequestParam("imagem") MultipartFile imagem) {
    try {
      usuarioServer.uploadImage(userId, imagem);
      return ResponseEntity.ok().build();
    } catch (IOException e) {
      return ResponseEntity.notFound().build();

    }
  }

  @GetMapping("/api/files/{id}")

  public ResponseEntity<byte[]> getFileById(@PathVariable String id) throws Exception {
    byte[] resource = usuarioServer.getFileAsJpg(id);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);

}


}
