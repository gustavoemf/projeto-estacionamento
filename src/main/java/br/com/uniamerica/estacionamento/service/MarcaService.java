package br.com.uniamerica.estacionamento.service;

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
    public void cadastrarMarca(Marca marca){
        if("".equals(marca.getNome())){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(marca.getNome().length() > 50){
            throw new RuntimeException("O nome da marca excede o máximo de caracteres (50)");
        }
        this.marcaRepository.save(marca);
    }

    @Transactional
    public void atualizarMarca(final Long id, Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if(marcaBanco==null || !marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("Não foi possível identificar o registro informado");
        }
        if("".equals(marca.getNome())){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(marca.getNome().length() > 50){
            throw new RuntimeException("O nome da marca excede o máximo de caracteres (50)");
        }
        this.marcaRepository.save(marca);
    }
}
