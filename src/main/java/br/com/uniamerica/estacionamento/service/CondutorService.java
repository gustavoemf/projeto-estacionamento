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

    @Transactional
    public void cadastraCondutor(Condutor condutor){
        if(condutor.getNome().isEmpty()){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(condutor.getCpf().isEmpty()){
            throw new RuntimeException("O campo cpf não pode ser nulo");
        }
        if(condutor.getTelefone().isEmpty()){
            throw new RuntimeException("O campo telefone não pode ser nulo");
        }
        if(condutor.getNome().length() > 100){
            throw new RuntimeException("O nome do condutor excede o máximo de caracteres (100)");
        }
        if(condutor.getCpf().length() > 15){
            throw new RuntimeException("O cpf do condutor excede o máximo de caracteres (15)");
        }
        if(condutor.getTelefone().length() > 17){
            throw new RuntimeException("O telefone do condutor excede o máximo de caracteres (70)");
        }
        this.condutorRepository.save(condutor);
    }

    @Transactional
    public void atualizaCondutor(final Long id, Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        if(condutorBanco==null || !condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("Não foi possível identificar o registro informado");
        }
        if(condutor.getNome().isEmpty()){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(condutor.getCpf().isEmpty()){
            throw new RuntimeException("O campo cpf não pode ser nulo");
        }
        if(condutor.getTelefone().isEmpty()){
            throw new RuntimeException("O campo telefone não pode ser nulo");
        }
        if(condutor.getNome().length() > 100){
            throw new RuntimeException("O nome do condutor excede o máximo de caracteres (100)");
        }
        if(condutor.getCpf().length() > 15){
            throw new RuntimeException("O cpf do condutor excede o máximo de caracteres (15)");
        }
        if(condutor.getTelefone().length() > 17){
            throw new RuntimeException("O telefone do condutor excede o máximo de caracteres (70)");
        }
        this.condutorRepository.save(condutor);
    }
}
