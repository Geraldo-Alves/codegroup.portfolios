package com.codegroup.portfolios.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.dto.MembroResumoDTO;
import com.codegroup.portfolios.dto.NovoMembroDTO;
import com.codegroup.portfolios.dto.UsuarioDTO;
import com.codegroup.portfolios.entities.Atribuicao;
import com.codegroup.portfolios.entities.Membro;
import com.codegroup.portfolios.entities.Pessoa;
import com.codegroup.portfolios.entities.Projeto;
import com.codegroup.portfolios.entities.Usuario;
import com.codegroup.portfolios.repositories.AtribuicaoRepository;
import com.codegroup.portfolios.repositories.MembroRepository;
import com.codegroup.portfolios.repositories.PessoaRepository;
import com.codegroup.portfolios.repositories.ProjetoRepository;
import com.codegroup.portfolios.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class MembroService {

    @Autowired
    private MembroRepository membroRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private AtribuicaoRepository atribuicaoRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public List<MembroResumoDTO> membrosProjeto(Long idProjeto) throws Exception {
        Optional<Projeto> projeto = projetoRepository.findById(idProjeto);

        if (projeto.isEmpty()) {
            throw new Exception("Projeto não encontrado");
        }

        return membroRepository.findResumoAtivoByProjeto(idProjeto);
    }

    @Transactional
    public Membro adicionarMembro(Long idProjeto, NovoMembroDTO dto) throws Exception {

        Boolean isGerente = dto.getIdAtribuicao() != null && dto.getIdAtribuicao() == 1L;

        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new Exception("Projeto não encontrado"));

        Atribuicao atribuicao = atribuicaoRepository.findById(dto.getIdAtribuicao())
                .orElseThrow(() -> new Exception("Atribuição não encontrada"));

        Pessoa pessoa;
        if (dto.getIdPessoa() != null) {
            pessoa = pessoaRepository.findById(dto.getIdPessoa())
                    .orElseThrow(() -> new Exception("Pessoa não encontrada"));
        } else {
            validarCamposNovaPessoa(dto);

            UsuarioDTO usuarioDTO = new UsuarioDTO(
                    dto.getEmail(),
                    dto.getNome(),
                    dto.getCpf(),
                    dto.getCelular(),
                    dto.getPerfis()
            );
            Long idUsuario = usuarioService.createUsuario(usuarioDTO);
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new Exception("Erro ao recuperar usuário criado"));
            pessoa = usuario.getRefIdPessoa();
        }

        // verificar se a atribuição é de gerente e se o projeto já possui um gerente responsável
        if (atribuicao.getId() == 1) {
            List<Membro> membrosProjeto = membroRepository.findByRefIdProjetoId(idProjeto);
            boolean gerenteExistente = membrosProjeto.stream()
                    .anyMatch(m -> m.getGerenteResponsavel() && m.getStatus().equals("A"));
            if (gerenteExistente) {
                throw new Exception("O projeto já possui um gerente responsável ativo");
            }
        }

        // verificar se o membro ja esta no projeto
        Optional<Membro> membroExistente = membroRepository.findByRefIdProjetoIdAndRefIdPessoaId(idProjeto, pessoa.getId());
        if (membroExistente.isPresent()) {
            throw new Exception("Membro já existe para este projeto");
        }

        // verificar se pessoa está presente em 3 outros projetos
        List<Membro> membrosPessoa = membroRepository.findByRefIdPessoaId(pessoa.getId());
        if (membrosPessoa.size() >= 3) {
            throw new Exception("Membro já está presente em 3 projetos");
        }

        // verificar se o projeto possui mais de 10 membros ativos
        List<Membro> membrosProjeto = membroRepository.findByRefIdProjetoId(idProjeto);
        long membrosAtivos = membrosProjeto.stream().filter(m -> m.getStatus().equals("A")).count();
        if (membrosAtivos >= 10) {
            throw new Exception("O Projeto já possui 10 membros ativos");
        }

        Membro membro = new Membro();
        membro.setRefIdProjeto(projeto);
        membro.setRefIdPessoa(pessoa);
        membro.setRefIdAtribuicao(atribuicao);
        membro.setGerenteResponsavel(isGerente);
        membro.setStatus("A");

        membroRepository.save(membro);

        return membro;
    }

    private void validarCamposNovaPessoa(NovoMembroDTO dto) throws Exception {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new Exception("Nome é obrigatório para criar novo membro");
        }
        if (dto.getCpf() == null || dto.getCpf().isBlank()) {
            throw new Exception("CPF é obrigatório para criar novo membro");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new Exception("E-mail é obrigatório para criar novo membro");
        }
    }

}
