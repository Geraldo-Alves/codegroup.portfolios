package com.codegroup.portfolios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.dto.UsuarioDTO;
import com.codegroup.portfolios.entities.Perfil;
import com.codegroup.portfolios.entities.PerfilUsuario;
import com.codegroup.portfolios.entities.Pessoa;
import com.codegroup.portfolios.entities.Usuario;
import com.codegroup.portfolios.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

import com.codegroup.portfolios.repositories.PerfilRepository;
import com.codegroup.portfolios.repositories.PerfilUsuarioRepository;
import com.codegroup.portfolios.repositories.PessoaRepository;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @Transactional
    public Long createUsuario(UsuarioDTO usuarioDTO) throws Exception {

        // salva a pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(usuarioDTO.getNome());
        pessoa.setCpf(usuarioDTO.getCpf());
        pessoa.setCelular(usuarioDTO.getCelular());
        pessoa = pessoaRepository.save(pessoa);

        // salva o usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(encoder.encode(usuarioDTO.getSenha()));
        usuario.setRefIdPessoa(pessoa);

        // salva lista de perfis
        List<Integer> perfis = usuarioDTO.getPerfis();
        if (perfis != null) {
            perfis.forEach(idPerfil -> {

                // ao dar exceção fazer rollback da transação
                Perfil perfil = perfilRepository.findById(idPerfil);
                if (perfil == null) {
                    throw new RuntimeException("Perfil não encontrado: " + idPerfil);
                }

                PerfilUsuario perfilUsuario = new PerfilUsuario();
                perfilUsuario.setRefIdPerfil(perfil);
                perfilUsuario.setRefIdUsuario(usuario);
                perfilUsuario.setStatus("A");
                perfilUsuario.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()).toLocalDateTime());
                perfilUsuarioRepository.save(perfilUsuario);
            });
        }

        // Salvar o usuário no banco de dados
        usuarioRepository.save(usuario);

        return usuario.getId();

    }

}
