package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional
    public void cadastraModelo(Modelo modelo){
        if("".equals(modelo.getMarca().getNome())){
            throw new RuntimeException("O campo marca não pode ser nul");
        }
        if("".equals(modelo.getNome())){
            throw new RuntimeException("O campo nome não pode ser nul");
        }
        this.modeloRepository.save(modelo);
    }

    @Transactional
    public void atualizaModelo(final Long id, Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if(modeloBanco==null || !modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("Não foi possível identificar o registro informado");
        }
        if("".equals(modelo.getMarca().getNome())){
            throw new RuntimeException("O campo marca não pode ser nulo");
        }
        if("".equals(modelo.getNome())){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if(modelo.getNome().length()>50){
            throw new RuntimeException("O nome do modelo excede o máximo de caracteres (50)");
        }
        this.modeloRepository.save(modelo);
    }
}
