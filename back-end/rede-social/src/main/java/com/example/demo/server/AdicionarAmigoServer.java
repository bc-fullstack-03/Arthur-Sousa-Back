package com.example.demo.server;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AdicionarAmigo;
import com.example.demo.model.Usuario;
import com.example.demo.repository.AdiconarAmigoRepository;
import com.example.demo.repository.UsuarioRepository;

@Service
public class AdicionarAmigoServer {

  @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdiconarAmigoRepository adiconarAmigoRepository;

    public void adicionar(UUID userId, UUID amigoId) throws Exception {
        // Verifique se os IDs são válidos
        if (!usuarioRepository.existsById(userId) || !usuarioRepository.existsById(amigoId)) {
            throw new Exception("IDs inválidos.");
        }

        // Verifique se a relação de amizade já existe
        Optional<AdicionarAmigo> amigo = adiconarAmigoRepository.findByUserIdAndAmigoId(userId, amigoId);
        if (amigo.isPresent()) {
            throw new Exception("Usuário já é amigo.");
        }

        // Crie uma nova relação de amizade
        var adicionar = new AdicionarAmigo(userId, amigoId);
        adiconarAmigoRepository.save(adicionar);
    }

    public List<Usuario> listaDeAmigos(UUID userId){
        // Recupere todas as relações de amizade do usuário
        List<AdicionarAmigo> verificar = adiconarAmigoRepository.findByUserId(userId);

             // Recupere todos os amigos do usuário
        List<UUID> amigosIds = verificar.stream().map(AdicionarAmigo :: getAmigoId).collect(Collectors.toList());
            
        List<Usuario> amigos = usuarioRepository.findAllById(amigosIds);

        return amigos;
    }

}
