package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.FormataNome;
import br.com.uniamerica.estacionamento.config.ValidaNome;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public void cadastraMarca(Marca marca){
        if(marca.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if("".equals(marca.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!ValidaNome.validaNome(marca.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(marcaRepository.findByNome(marca.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marca.getCadastro() == null){
            marca.setCadastro(LocalDateTime.now());
        }
        marca.setNome(FormataNome.formataNome(marca.getNome()));
        marca.setAtivo(true);
        this.marcaRepository.save(marca);
    }

    @Transactional
    public void atualizaMarca(final Long id, Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if(marcaBanco==null || !marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if("".equals(marca.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!ValidaNome.validaNome(marca.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(marcaRepository.findByNome(marca.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marca.getAtualizacao() == null){
            marca.setAtualizacao(LocalDateTime.now());
        }
        if(marca.getCadastro() != null){
            throw new RuntimeException("é impossível alterar a data de cadastro");
        }
        marca.setNome(FormataNome.formataNome(marca.getNome()));
        this.marcaRepository.save(marca);
    }
}
