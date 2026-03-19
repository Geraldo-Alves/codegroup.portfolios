package com.codegroup.portfolios.services;

import com.codegroup.portfolios.repositories.AndamentoRepository;
import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.dto.ProjetoDTO;
import com.codegroup.portfolios.entities.Andamento;
import com.codegroup.portfolios.entities.Atribuicao;
import com.codegroup.portfolios.entities.Membro;
import com.codegroup.portfolios.entities.Pessoa;
import com.codegroup.portfolios.entities.Projeto;
import com.codegroup.portfolios.entities.StatusProjeto;
import com.codegroup.portfolios.repositories.AtribuicaoRepository;
import com.codegroup.portfolios.repositories.MembroRepository;
import com.codegroup.portfolios.repositories.PessoaRepository;
import com.codegroup.portfolios.repositories.ProjetoRepository;
import com.codegroup.portfolios.repositories.StatusProjetoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjetoService {

    private final AndamentoRepository andamentoRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private StatusProjetoRepository statusProjetoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private AtribuicaoRepository atribuicaoRepository;
    @Autowired
    private MembroRepository membroRepository;

    ProjetoService(AndamentoRepository andamentoRepository) {
        this.andamentoRepository = andamentoRepository;
    }

    @Transactional
    public Long createProjeto(ProjetoDTO projetoDTO) throws Exception {

        Projeto projeto = new Projeto();
        projeto.setNome(projetoDTO.getNome());
        projeto.setDescricao(projetoDTO.getDescricao());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataPrevisaoTermino(projetoDTO.getDataPrevisaoTermino());

        if (projetoDTO.getDataTermino() != null) {
            projeto.setDataTermino(projetoDTO.getDataTermino());
        }
        projeto.setOrcamentoTotal(projetoDTO.getOrcamentoTotal());
        projetoRepository.save(projeto);

        // define o gerente do projeto
        Optional<Pessoa> pessoaGerente = pessoaRepository.findById(projetoDTO.getIdMembroGerente());
        if (pessoaGerente.isEmpty()) {
            throw new Exception("Gerente não encontrado");
        }

        // obtem atribuição de gerente
        Optional<Atribuicao> atribuicaoGerente = atribuicaoRepository.findById(1L);
        if (atribuicaoGerente.isEmpty()) {
            throw new Exception("Atribuição de Gerente não encontrada");
        }

        // cria o membro gerente
        Membro membroGerente = new Membro();
        membroGerente.setRefIdPessoa(pessoaGerente.get());
        membroGerente.setRefIdProjeto(projeto);
        membroGerente.setRefIdAtribuicao(atribuicaoGerente.get());
        membroGerente.setStatus("A");
        membroGerente.setGerenteResponsavel(true);
        membroRepository.save(membroGerente);

        // define o primeiro andamento
        Optional<StatusProjeto> status = statusProjetoRepository.findById(projetoDTO.getIdStatusAtual());
        if (status.isEmpty()) {
            throw new Exception("Status não encontrado");
        }

        Andamento andamento = new Andamento();
        andamento.setRefIdProjeto(projeto);
        andamento.setRefIdStatus(status.get());
        andamento.setRefIdMembro(membroGerente);
        andamento.setAtual(true);
        andamentoRepository.save(andamento);

        return projeto.getId();

    }

    @Transactional
    public String transicaoAndamento(Long idProjeto, Long idNovoStatus) throws Exception {

        Optional<Projeto> projeto = projetoRepository.findById(idProjeto);

        if (projeto.isEmpty()) {
            throw new Exception("Projeto não encontrado");
        }

        // status atual do projeto
        Optional<Andamento> andamentoAtualOpt = andamentoRepository.findByRefIdProjetoIdAndAtualTrue(idProjeto);
        if (andamentoAtualOpt.isEmpty()) {
            throw new Exception("Andamento atual do projeto não encontrado");
        }

        System.out.println("### Andamento: " + andamentoAtualOpt.get().getId() + " - Status: "
                + andamentoAtualOpt.get().getRefIdStatus().getNome());

        Optional<StatusProjeto> statusAtual = statusProjetoRepository
                .findById(andamentoAtualOpt.get().getRefIdStatus().getId());
        if (statusAtual.isEmpty()) {
            throw new Exception("Status atual não encontrado");
        }

        System.out.println("### Status atual: " + statusAtual.get().getNome());

        // novo status solicitado
        Optional<StatusProjeto> novoStatus = statusProjetoRepository.findById(idNovoStatus);
        if (novoStatus.isEmpty()) {
            throw new Exception("Novo status não encontrado");
        }

        System.out.println("### Novo status solicitado: " + novoStatus.get().getNome());

        /**
         * Validações para transição de status
         */
        // realiza as validacoes
        // caso novo status seja cancelado, permite transacao
        if (!novoStatus.get().getNome().equalsIgnoreCase("cancelado")) {

            // proximo status permitido pelo status filho atual
            Optional<StatusProjeto> proximoStatus = statusProjetoRepository
                    .findByStatusPaiId(statusAtual.get().getId());
            if (proximoStatus.isEmpty()) {
                throw new Exception("### Próximo status não encontrado");
            }

            System.out.println("### Proximo status: " + proximoStatus.get().getNome());

            // se proximo status for diferente do novo status solicitado, bloqueia transacao
            if (!proximoStatus.get().getId().equals(novoStatus.get().getId())) {
                throw new Exception("Transição de status inválida");
            }

        }

        // atualiza andamento atual para false
        andamentoAtualOpt.get().setAtual(false);
        andamentoRepository.save(andamentoAtualOpt.get());

        Andamento novoAndamento = new Andamento();
        novoAndamento.setRefIdProjeto(projeto.get());
        novoAndamento.setRefIdStatus(novoStatus.get());
        novoAndamento.setRefIdMembro(andamentoAtualOpt.get().getRefIdMembro());
        novoAndamento.setAtual(true);
        andamentoRepository.save(novoAndamento);

        return "Projeto alterado de: " +statusAtual.get().getNome() + ", para: " + novoStatus.get().getNome();

    }

}
