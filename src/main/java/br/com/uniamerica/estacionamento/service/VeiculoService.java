package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaPlaca;
import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ValidaPlaca validaPlaca;

    @Transactional
    public void cadastraVeiculo(Veiculo veiculo){
        if(veiculo.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("o campo placa não pode ser vazio");
        }
        if("".equals(veiculo.getTipo().toString())){
            throw new RuntimeException("o campo tipo não pode ser vazio");
        }
        if("".equals(veiculo.getCor().toString())){
            throw new RuntimeException("o campo cor não pode ser vazio");
        }
        if(veiculo.getAno() == 0 || veiculo.getAno() > LocalDate.now().getYear()){
            throw new RuntimeException("o campo ano não pode ser zero ou maior que o ano atual");
        }
        if(!veiculo.getTipo().toString().equals(veiculo.getTipo().toString().toUpperCase())){
            throw new RuntimeException("o campo tipo deve ser inserido em maiúsculo (CARRO, MOTO, VAN)");
        }
        if(!veiculo.getCor().toString().equals(veiculo.getCor().toString().toUpperCase())){
            throw new RuntimeException("o campo cor deve ser inserido em maiúsculo (PRETO, AZUL, PRATA...)");
        }
        if(veiculoRepository.findByPlaca(veiculo.getPlaca())!=null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(!ValidaPlaca.validaPlaca(veiculo.getPlaca())){
            throw new RuntimeException("a placa não condiz com a formatação necessária");
        }
        if(veiculo.getCadastro() == null){
            veiculo.setCadastro(LocalDateTime.now());
        }
        veiculo.setAtivo(true);
        this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public void atualizaVeiculo(final Long id, Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoBanco==null || !veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("Não foi possivel encontrar o registro informado");
        }
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("o campo placa não pode ser vazio");
        }
        if("".equals(veiculo.getTipo().toString())){
            throw new RuntimeException("o campo tipo não pode ser vazio");
        }
        if("".equals(veiculo.getCor().toString())){
            throw new RuntimeException("o campo cor não pode ser vazio");
        }
        if(veiculoRepository.findByPlaca(veiculo.getPlaca())!=null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(!ValidaPlaca.validaPlaca(veiculo.getPlaca())){
            throw new RuntimeException("a placa não condiz com a formatação necessária");
        }
        if(veiculo.getAtualizacao() == null){
            veiculo.setAtualizacao(LocalDateTime.now());
        }
        if(veiculo.getCadastro() != null){
            throw new RuntimeException("é impossível alterar a data de cadastro");
        }
        this.veiculoRepository.save(veiculo);
    }
}
