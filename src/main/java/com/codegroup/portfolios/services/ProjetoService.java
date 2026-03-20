package com.codegroup.portfolios.services;

import com.codegroup.portfolios.repositories.AndamentoRepository;
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

    @Autowired
    private AndamentoRepository andamentoRepository;
    @Autowired
    private ProjetoRepository projetoRepository;
    @Autowired
    private StatusProjetoRepository statusProjetoRepository;
    @Autowired
    private MembroService membroService;

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

        projetoDTO.getMembroGerente().setIdAtribuicao(1L);
        Membro membroGerente = membroService.adicionarMembro(projeto.getId(), projetoDTO.getMembroGerente());

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
        Optional<Andamento> andamentoAtual = andamentoRepository.findByRefIdProjetoIdAndAtualTrue(idProjeto);
        if (andamentoAtual.isEmpty()) {
            throw new Exception("Andamento atual do projeto não encontrado");
        }

        Optional<StatusProjeto> statusAtual = statusProjetoRepository
                .findById(andamentoAtual.get().getRefIdStatus().getId());
        if (statusAtual.isEmpty()) {
            throw new Exception("Status atual não encontrado");
        }

        // novo status solicitado
        Optional<StatusProjeto> novoStatus = statusProjetoRepository.findById(idNovoStatus);
        if (novoStatus.isEmpty()) {
            throw new Exception("Novo status não encontrado");
        }

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

            // se proximo status for diferente do novo status solicitado, bloqueia transacao
            if (!proximoStatus.get().getId().equals(novoStatus.get().getId())) {
                throw new Exception("Transição de status inválida");
            }

        }

        // atualiza andamento atual para false
        andamentoAtual.get().setAtual(false);
        andamentoRepository.save(andamentoAtual.get());

        Andamento novoAndamento = new Andamento();
        novoAndamento.setRefIdProjeto(projeto.get());
        novoAndamento.setRefIdStatus(novoStatus.get());
        novoAndamento.setRefIdMembro(andamentoAtual.get().getRefIdMembro());
        novoAndamento.setAtual(true);
        andamentoRepository.save(novoAndamento);

        return "Projeto alterado de: " +statusAtual.get().getNome() + ", para: " + novoStatus.get().getNome();

    }

}
