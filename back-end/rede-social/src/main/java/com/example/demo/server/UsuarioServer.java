package com.example.demo.server;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.JwtUtil;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

import io.jsonwebtoken.io.IOException;

@Service
public class UsuarioServer {

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private GridFsTemplate gridFsTemplate;

  public ResponseEntity<Usuario> criar(Usuario usuario) throws Exception {
    String email = usuario.getEmail();
    if (!email.contains("@") || !email.contains(".")) {
      throw new Exception("E-mail inválido");
    }

    // Criptografa a senha do usuário
    String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());

    // Cria um novo usuário com a senha criptografada
    Usuario novoUsuario = new Usuario(usuario.getNome(), usuario.getEmail(), senhaCriptografada);

    // Salva o usuário criptografado no banco de dados
    novoUsuario = usuarioRepository.save(novoUsuario);

    // Retorna a resposta com o novo usuário criado
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

  public String autenticar(String email, String senha) throws Exception {
    Usuario user = usuarioRepository.findByEmail(email);

    if (user == null) {
      throw new Exception("Usuário não encontrado");
    }

    if (!passwordEncoder.matches(senha, user.getSenha())) {
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

  public void uploadImage(UUID userId, MultipartFile image) throws IOException, java.io.IOException {
    Optional<Usuario> optionalUser = usuarioRepository.findById(userId);
    if (optionalUser.isPresent()) {
      Usuario user = optionalUser.get();
      String fileName = StringUtils.cleanPath(image.getOriginalFilename());
      DBObject metaData = new BasicDBObject();
      metaData.put("userId", user.getId());
      metaData.put("fileName", fileName);
      metaData.put("fileType", image.getContentType());
      ObjectId objectId = gridFsTemplate.store(image.getInputStream(), fileName, metaData);
      user.setImageUrl("/usuario/api/files/" + objectId.toHexString());
      usuarioRepository.save(user);
    } else {
      throw new IOException("Usuário com ID " + userId + " não encontrado");

    }

  }
  public Usuario findUserById(UUID id) {
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado para o ID fornecido: " + id));
}
public byte[] getFileAsJpg(String id) throws Exception {
  GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(id))));
  if (file != null) {
      GridFsResource resource = gridFsTemplate.getResource(file);
      BufferedImage  image = ImageIO.read(resource.getInputStream());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "jpg", outputStream);
      return outputStream.toByteArray();
  } else {
      throw new Exception("Arquivo não encontrado com ID " + id);
  }
}

}


