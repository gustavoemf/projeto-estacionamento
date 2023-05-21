package br.com.uniamerica.estacionamento.service;

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
    public void cadastrarMarca(Marca marca){
        if("".equals(marca.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(marcaRepository.findByNome(marca.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marca.getCadastro() == null){
            marca.setCadastro(LocalDateTime.now());
        }
        this.marcaRepository.save(marca);
    }

    @Transactional
    public void atualizarMarca(final Long id, Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if(marcaBanco==null || !marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if("".equals(marca.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(marcaRepository.findByNome(marca.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marca.getAtualizacao() == null){
            marca.setAtualizacao(LocalDateTime.now());
        }
        this.marcaRepository.save(marca);
    }
}
