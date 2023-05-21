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
        if(veiculo.getPlaca() != null){
            veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        }
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("o campo placa não pode ser vazio");
        }
        if(veiculo.getAno() == 0 || veiculo.getAno() > LocalDate.now().getYear()){
            throw new RuntimeException("o campo ano não pode ser zero ou maior que o ano atual");
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
        if(veiculoRepository.findByPlaca(veiculo.getPlaca())!=null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(!ValidaPlaca.validaPlaca(veiculo.getPlaca())){
            throw new RuntimeException("a placa não condiz com a formatação necessária");
        }
        if(veiculo.getAtualizacao() == null){
            veiculo.setAtualizacao(LocalDateTime.now());
        }
        this.veiculoRepository.save(veiculo);
    }
}
