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
        if(modelo.getNome() == null){
            throw new RuntimeException("o campo nome não pode ser nulo");
        }
        if(modelo.getNome().length() > 50 || modelo.getNome().length() < 2){
            throw new RuntimeException("o nome do modelo não respeita a quantidade de caracteres necessária (2-50)");
        }
        if(modelo.getMarca() == null){
            throw new RuntimeException("o campo marca não pode ser nulo");
        }
        if("".equals(modelo.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(!Validacoes.validaModelo(modelo.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(modeloRepository.findByNome(modelo.getNome())!=null){
            throw new RuntimeException("o campo nome já existe");
        }
        if(marcaRepository.findById(modelo.getMarca().getId()).isEmpty()){
            throw new RuntimeException("o id da marca inserido não existe");
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
        if("".equals(modelo.getNome())){
            throw new RuntimeException("o campo nome não pode ser vazio");
        }
        if(modelo.getNome().length() > 50 || modelo.getNome().length() < 2){
            throw new RuntimeException("o nome do modelo não respeita a quantidade de caracteres necessária (2-50)");
        }
        if(!Validacoes.validaModelo(modelo.getNome())){
            throw new RuntimeException("o campo nome possui caracteres inválidos");
        }
        if(marcaRepository.findById(modelo.getMarca().getId()).isEmpty()){
            throw new RuntimeException("o id de marca inserido não existe");
        }
        if(!modelo.getNome().equals(modeloRepository.findById(modelo.getId()).get().getNome())){
            throw new RuntimeException("o campo nome já existe");
        }
        modelo.setNome(Validacoes.formataNome(modelo.getNome()));
        if(modelo.isAtivo() == modeloRepository.findById(modelo.getId()).get().isAtivo()){
            modelo.setAtivo(modeloRepository.findById(modelo.getId()).get().isAtivo());
        } else {
            modelo.setAtivo(!modeloRepository.findById(modelo.getId()).get().isAtivo());
        }
        if(modelo.getCadastro() == null){
            modelo.setCadastro(modeloRepository.findById(modelo.getId()).get().getCadastro());
        }
        this.modeloRepository.save(modelo);
    }
}
