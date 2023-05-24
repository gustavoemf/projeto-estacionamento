package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.Validacoes;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.repository.MarcaRepository;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public void cadastraModelo(Modelo modelo){
        if(modelo.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if("".equals(modelo.getNome()) && modelo.getNome() != null){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!Validacoes.validaModelo(modelo.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(modeloRepository.findByNome(modelo.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marcaRepository.findById(modelo.getMarca().getId()).isEmpty()){
            throw new RuntimeException("o id de marca inserido não existe");
        }
        modelo.setNome(Validacoes.formataNome(modelo.getNome()));
        this.modeloRepository.save(modelo);
    }

    @Transactional
    public void atualizaModelo(final Long id, Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if(modeloBanco==null || !modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if("".equals(modelo.getNome()) && modelo.getNome() != null){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!Validacoes.validaModelo(modelo.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        /*if(modeloRepository.findByNome(modelo.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }*/
        if(marcaRepository.findById(modelo.getMarca().getId()).isEmpty()){
            throw new RuntimeException("o id de marca inserido não existe");
        }
        modelo.setNome(Validacoes.formataNome(modelo.getNome()));
        this.modeloRepository.save(modelo);
    }
}
