package com.example.demo.server;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.model.JwtUtil;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioServer {

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  private JwtUtil jwtUtil;

  public ResponseEntity<Usuario> criar(Usuario usuario) {
    Usuario novoUsuario = usuarioRepository.save(usuario);
    return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
  }

  public List<Usuario> usuarios() {
    return usuarioRepository.findAll();
  }

  public Usuario buscarPorId(UUID id) {
    return usuarioRepository.findById(id).orElse(null);

  }

  public long countUsuarios() {
    return usuarioRepository.count();
  }

  public void excluirUsuarios(UUID id) {
    usuarioRepository.deleteById(id);
  }

  public ResponseEntity<Usuario> atualizarUsuario(UUID id, Usuario usuario) {
    Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
    if (optionalUsuario.isPresent()) {
      Usuario usuarioExistente = optionalUsuario.get();
      usuarioExistente.setNome(usuario.getNome());
      usuarioExistente.setEmail(usuario.getEmail());
      // Adicione outros campos que deseja atualizar

      Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
      return ResponseEntity.ok().body(usuarioAtualizado);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


 
  public String autenticar(String email, String senha) throws Exception{
    Usuario user = usuarioRepository.findByEmail(email);

    if (user == null) {
        throw new Exception("Usuário não encontrado");
    }

    if (!user.getSenha().equals(senha)) {
        throw new Exception("Senha incorreta");
    }

    String token = jwtUtil.gerarToken(user.getId());
    return token;

  }

  public Usuario getUserFromToken(String token) throws Exception {
    if (!jwtUtil.validarToken(token)) {
        throw new Exception("Invalid token");
    }
    String email = jwtUtil.getEmailFromToken(token);
    Usuario user = usuarioRepository.findByEmail(email);
    return user;
}





}
