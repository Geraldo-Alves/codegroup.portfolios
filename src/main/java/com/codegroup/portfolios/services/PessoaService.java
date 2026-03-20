package com.codegroup.portfolios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegroup.portfolios.entities.Pessoa;
import com.codegroup.portfolios.repositories.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa criarPessoa(String nome, String cpf, String celular) throws Exception {
        if (pessoaRepository.existsByCpf(cpf)) {
            throw new Exception("CPF já cadastrado: " + cpf);
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        pessoa.setCelular(celular);

        return pessoaRepository.save(pessoa);
    }

}
