package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.Validacoes;
import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public void cadastraMarca(Marca marca){
        if(marca.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if(marca.getNome() == null){
            throw new RuntimeException("o campo nome não pode ser nulo");
        }
        if("".equals(marca.getNome()) && marca.getNome() != null){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!Validacoes.validaNome(marca.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(marcaRepository.findByNome(marca.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        marca.setNome(Validacoes.formataNome(marca.getNome()));
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
        if(!Validacoes.validaNome(marca.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        marca.setNome(Validacoes.formataNome(marca.getNome()));
        if(marca.isAtivo() != marcaRepository.findById(marca.getId()).get().isAtivo()){
            marca.isAtivo();
        }
        if(marca.getCadastro() == null){
            marca.setCadastro(marcaRepository.findById(marca.getId()).get().getCadastro());
        }
        this.marcaRepository.save(marca);
    }
}
