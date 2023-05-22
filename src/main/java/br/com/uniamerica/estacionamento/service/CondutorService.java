package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.FormataNome;
import br.com.uniamerica.estacionamento.config.ValidaCpf;
import br.com.uniamerica.estacionamento.config.ValidaNome;
import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Condutor;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private ValidaCpf validaCpf;

    @Transactional
    public void cadastraCondutor(Condutor condutor){
        if(condutor.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if("".equals(condutor.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(ValidaNome.validaNome(condutor.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if("".equals(condutor.getCpf())){
            throw new RuntimeException("o campo cpf não pode ser vazio");
        }
        if("".equals(condutor.getTelefone())){
            throw new RuntimeException("o campo telefone não pode ser vazio");
        }
        if(condutor.getTempoPago() != null){
            throw new RuntimeException("o campo tempoPago não deve ser inserido");
        }
        if(condutor.getTempoDesconto() != null){
            throw new RuntimeException("o campo tempoPago não deve ser inserido");
        }
        if(condutorRepository.findByCpf(condutor.getCpf())!=null){
            throw new RuntimeException("o campo cpf já existe");
        }
        if(condutorRepository.findByTelefone(condutor.getTelefone())!=null){
            throw new RuntimeException("o campo telefone já existe");
        }
        if(!this.validaCpf.isCPF(condutor.getCpf())){
            throw new RuntimeException("o cpf não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(condutor.getTelefone())){
            throw new RuntimeException("o telefone não condiz com a formatação necessária");
        }
        if(condutor.getCadastro() == null){
            condutor.setCadastro(LocalDateTime.now());
        }
        condutor.setNome(FormataNome.formataNome(condutor.getNome()));
        condutor.setAtivo(true);
        this.condutorRepository.save(condutor);
    }

    @Transactional
    public void atualizaCondutor(final Long id, Condutor condutor){
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        if(condutorBanco==null || !condutorBanco.getId().equals(condutor.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if("".equals(condutor.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(ValidaNome.validaNome(condutor.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if("".equals(condutor.getCpf())){
            throw new RuntimeException("o campo cpf não pode ser vazio");
        }
        if("".equals(condutor.getTelefone())){
            throw new RuntimeException("o campo telefone não pode ser vazio");
        }
        if(condutor.getTempoPago() != null){
            throw new RuntimeException("o campo tempoPago não deve ser inserido");
        }
        if(condutor.getTempoDesconto() != null){
            throw new RuntimeException("o campo tempoDesconto não deve ser inserido");
        }
        if(condutorRepository.findByCpf(condutor.getCpf())!=null){
            throw new RuntimeException("o campo cpf já existe");
        }
        if(condutorRepository.findByTelefone(condutor.getTelefone())!=null){
            throw new RuntimeException("o campo telefone já existe");
        }
        if(!this.validaCpf.isCPF(condutor.getCpf())){
            throw new RuntimeException("o cpf não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(condutor.getTelefone())){
            throw new RuntimeException("o telefone não condiz com a formatação necessária");
        }
        if(condutor.getAtualizacao() == null){
            condutor.setAtualizacao(LocalDateTime.now());
        }
        if(condutor.getCadastro() != null){
            throw new RuntimeException("é impossível alterar a data de cadastro");
        }
        condutor.setNome(FormataNome.formataNome(condutor.getNome()));
        this.condutorRepository.save(condutor);
    }
}
