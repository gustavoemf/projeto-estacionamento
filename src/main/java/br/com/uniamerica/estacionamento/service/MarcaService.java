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
        if(marca.getNome() == null || marca.getNome().isEmpty()){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(marca.getNome().length() > 70) {
            throw new RuntimeException("O nome do modelo excede o máximo de caracteres (70)");
        }
        this.marcaRepository.save(marca);
    }
}
