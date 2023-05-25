package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.Validacoes;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.ModeloRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;

    @Transactional
    public void cadastraVeiculo(Veiculo veiculo){
        if(veiculo.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if(veiculo.getPlaca() == null){
            throw new RuntimeException("o campo placa não pode ser nulo");
        }
        if(veiculo.getModelo() == null){
            throw new RuntimeException("o campo modelo não pode ser nulo");
        }
        if(veiculo.getCor() == null){
            throw new RuntimeException("o campo cor não pode ser nulo");
        }
        if(veiculo.getTipo() == null){
            throw new RuntimeException("o campo tipo não pode ser nulo");
        }
        if(modeloRepository.findById(veiculo.getModelo().getId()).isEmpty()){
            throw new RuntimeException("o id do modelo inserido não existe");
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
        if(veiculo.getAno() == 0 || veiculo.getAno() > LocalDate.now().getYear()){
            throw new RuntimeException("o campo ano não pode ser zero ou maior que o ano atual");
        }
        if(!veiculo.getTipo().toString().equals(veiculo.getTipo().toString().toUpperCase())){
            throw new RuntimeException("o campo tipo deve ser inserido em maiúsculo (CARRO, MOTO, VAN)");
        }
        if(!veiculo.getCor().toString().equals(veiculo.getCor().toString().toUpperCase())){
            throw new RuntimeException("o campo cor deve ser inserido em maiúsculo (PRETO, AZUL, PRATA...)");
        }
        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        if(veiculoRepository.findByPlaca(veiculo.getPlaca())!=null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(!Validacoes.validaPlaca(veiculo.getPlaca())){
            throw new RuntimeException("a placa não condiz com a formatação necessária");
        }
        this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public void atualizaVeiculo(final Long id, Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoBanco==null || !veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("não foi possivel encontrar o registro informado");
        }
        if(modeloRepository.findById(veiculo.getModelo().getId()).isEmpty()){
            throw new RuntimeException("o id do modelo inserido não existe");
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
        if(veiculo.getAno() == 0 || veiculo.getAno() > LocalDate.now().getYear()){
            throw new RuntimeException("o campo ano não pode ser zero ou maior que o ano atual");
        }
        if(!veiculo.getTipo().toString().equals(veiculo.getTipo().toString().toUpperCase())){
            throw new RuntimeException("o campo tipo deve ser inserido em maiúsculo (CARRO, MOTO, VAN)");
        }
        if(!veiculo.getCor().toString().equals(veiculo.getCor().toString().toUpperCase())){
            throw new RuntimeException("o campo cor deve ser inserido em maiúsculo (PRETO, AZUL, PRATA...)");
        }
        veiculo.setPlaca(veiculo.getPlaca().toUpperCase());
        if(!Validacoes.validaPlaca(veiculo.getPlaca())){
            throw new RuntimeException("a placa não condiz com a formatação necessária");
        }
        if(!veiculo.getPlaca().equals(veiculoRepository.findById(veiculo.getId()).get().getPlaca())){
            throw new RuntimeException("o campo placa não pode ser atualizado");
        }
        if(veiculo.isAtivo() == veiculoRepository.findById(veiculo.getId()).get().isAtivo()){
            veiculo.setAtivo(veiculoRepository.findById(veiculo.getId()).get().isAtivo());
        } else{
            veiculo.setAtivo(!veiculoRepository.findById(veiculo.getId()).get().isAtivo());
        }
        if(veiculo.getCadastro() == null){
            veiculo.setCadastro(veiculoRepository.findById(veiculo.getId()).get().getCadastro());
        }
        this.veiculoRepository.save(veiculo);
    }
}
