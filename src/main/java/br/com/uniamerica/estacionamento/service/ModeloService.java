package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional
    public void cadastraModelo(Modelo modelo){
        if(modelo.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if("".equals(modelo.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(modeloRepository.findByNome(modelo.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(modelo.getCadastro() == null){
            modelo.setCadastro(LocalDateTime.now());
        }
        this.modeloRepository.save(modelo);
    }

    @Transactional
    public void atualizaModelo(final Long id, Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if(modeloBanco==null || !modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("mão foi possível identificar o registro informado");
        }
        if("".equals(modelo.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(modeloRepository.findByNome(modelo.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(modelo.getAtualizacao() == null){
            modelo.setAtualizacao(LocalDateTime.now());
        }
        this.modeloRepository.save(modelo);
    }
}
