package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCpf;
import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private ValidaTelefone validaTelefone;
    @Autowired
    private ValidaCpf validaCpf;

    @Transactional
    public void cadastraCondutor(Condutor condutor){
        if(!this.validaCpf.isCPF(condutor.getCpf())){
            throw new RuntimeException("o cpf não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(condutor.getTelefone())){
            throw new RuntimeException("o telefone não condiz com a formatação necessária");
        }
        this.condutorRepository.save(condutor);
    }

    @Transactional
    public void atualizaCondutor(final Long id, Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        if(condutorBanco==null || !condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if(!this.validaCpf.isCPF(condutor.getCpf())){
            throw new RuntimeException("o cpf não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(condutor.getTelefone())){
            throw new RuntimeException("o telefone não condiz com a formatação necessária");
        }
        this.condutorRepository.save(condutor);
    }
}
