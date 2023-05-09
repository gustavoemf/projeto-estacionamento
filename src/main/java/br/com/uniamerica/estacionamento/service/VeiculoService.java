package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public void cadastraVeiculo(Veiculo veiculo){
        if(veiculo.getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if(veiculo.getModelo() == null){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca() == null){
            throw new RuntimeException("O campo marca do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo nome da marca do modelo não pode ser nulo");
        }
        if(veiculo.getCor() == null){
            throw new RuntimeException("O campo cor não pode ser nulo");
        }
        if(veiculo.getTipo() == null){
            throw new RuntimeException("O campo tipo não pode ser nulo");
        }
        if(veiculo.getAno() == 0){
            throw new RuntimeException("O campo ano não pode ser zero");
        }
        if(veiculo.getPlaca().length() > 7){
            throw new RuntimeException("A placa do condutor excede o máximo de caracteres (7)");
        }
        this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public void atualizaVeiculo(final Long id, Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoBanco==null || !veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("Não foi possivel encontrar o registro informado");
        }
        if(veiculo.getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if(veiculo.getModelo() == null){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca() == null){
            throw new RuntimeException("O campo marca do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo nome da marca do modelo não pode ser nulo");
        }
        if(veiculo.getCor() == null){
            throw new RuntimeException("O campo cor não pode ser nulo");
        }
        if(veiculo.getTipo() == null){
            throw new RuntimeException("O campo tipo não pode ser nulo");
        }
        if(veiculo.getAno() == 0){
            throw new RuntimeException("O campo ano não pode ser zero");
        }
        if(veiculo.getPlaca().length() > 7){
            throw new RuntimeException("A placa do condutor excede o máximo de caracteres (7)");
        }
        this.veiculoRepository.save(veiculo);
    }
}
