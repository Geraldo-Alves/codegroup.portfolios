package com.codegroup.portfolios.controllers;

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

import com.codegroup.portfolios.dto.NovoMembroDTO;
import com.codegroup.portfolios.dto.NovoStatusDTO;
import com.codegroup.portfolios.dto.ProjetoDTO;
import com.codegroup.portfolios.dto.Response;
import com.codegroup.portfolios.entities.Membro;
import com.codegroup.portfolios.entities.Projeto;
import com.codegroup.portfolios.repositories.ProjetoRepository;
import com.codegroup.portfolios.services.MembroService;
import com.codegroup.portfolios.services.ProjetoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/projeto") 
public class ProjetoController extends BaseController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private MembroService membroService;

    @GetMapping
    public Response findAll() {
        return super.success(projetoRepository.findAll());
    }

    @GetMapping("/status/{statusId}")
    public Response findByStatus(@PathVariable Integer statusId) {
        return super.success(projetoRepository.findResumoByStatusAtual(statusId.longValue()));
    }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Long id) {
        return super.success(projetoRepository.findResumoById(id));
    }

    @PostMapping
    public ResponseEntity<Response> save(@Valid @RequestBody ProjetoDTO projetoDto) throws Exception {
        return super.created(projetoService.createProjeto(projetoDto));
    }

    @PutMapping("/{id}/andamento")
    public Response transicaoStatusAndamento(@PathVariable Long id, @RequestBody NovoStatusDTO novoStatusDTO) throws Exception {
        return super.success(projetoService.transicaoAndamento(id, novoStatusDTO.getIdNovoStatus()));
    }

    @GetMapping("/{id}/membro")
    public Response membrosProjeto(@PathVariable Long id) throws Exception {
        return super.success(membroService.membrosProjeto(id));
    }

    @PostMapping("/{id}/membro")
    public Response membrosProjeto(@PathVariable Long id, @RequestBody NovoMembroDTO novoMembroDTO) throws Exception {
        return super.success(membroService.adicionarMembro(id, novoMembroDTO));
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Long id, @RequestBody Projeto projeto) {
        projeto.setId(id);
        return super.success(projetoRepository.save(projeto));
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id) {
        projetoRepository.deleteById(id);
        return super.success(null);
    }

}
