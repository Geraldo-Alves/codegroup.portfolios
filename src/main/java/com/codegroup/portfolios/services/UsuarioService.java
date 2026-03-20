package com.codegroup.portfolios.services;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.dto.UsuarioDTO;
import com.codegroup.portfolios.entities.Perfil;
import com.codegroup.portfolios.entities.PerfilUsuario;
import com.codegroup.portfolios.entities.Pessoa;
import com.codegroup.portfolios.entities.Usuario;
import com.codegroup.portfolios.repositories.PerfilRepository;
import com.codegroup.portfolios.repositories.PerfilUsuarioRepository;
import com.codegroup.portfolios.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$!";
    private static final int TAMANHO_SENHA = 10;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PerfilUsuarioRepository perfilUsuarioRepository;

    @Transactional
    public Long createUsuario(UsuarioDTO usuarioDTO) throws Exception {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new Exception("E-mail já cadastrado: " + usuarioDTO.getEmail());
        }

        Pessoa pessoa = pessoaService.criarPessoa(
                usuarioDTO.getNome(),
                usuarioDTO.getCpf(),
                usuarioDTO.getCelular()
        );

        String senhaGerada = gerarSenhaAleatoria();

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(encoder.encode(senhaGerada));
        usuario.setRefIdPessoa(pessoa);
        usuarioRepository.save(usuario);

        List<Integer> perfis = usuarioDTO.getPerfis();
        if (perfis != null) {
            perfis.forEach(idPerfil -> {
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

        emailService.enviarCredenciais(usuario.getEmail(), pessoa.getNome(), usuario.getEmail(), senhaGerada);

        return usuario.getId();
    }

    private String gerarSenhaAleatoria() {
        StringBuilder senha = new StringBuilder(TAMANHO_SENHA);
        for (int i = 0; i < TAMANHO_SENHA; i++) {
            senha.append(CARACTERES.charAt(RANDOM.nextInt(CARACTERES.length())));
        }
        return senha.toString();
    }

}
