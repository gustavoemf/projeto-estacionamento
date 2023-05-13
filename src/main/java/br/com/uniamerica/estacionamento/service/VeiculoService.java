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
            throw new RuntimeException("o campo placa não pode ser nulo");
        }
        if(veiculoRepository.findByPlaca(veiculo.getPlaca()) != null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(veiculo.getModelo() == null){
            throw new RuntimeException("o campo modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca() == null){
            throw new RuntimeException("o campo marca do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("o campo nome da marca do modelo não pode ser nulo");
        }
        if(veiculo.getCor() == null){
            throw new RuntimeException("o campo cor não pode ser nulo");
        }
        if(veiculo.getTipo() == null){
            throw new RuntimeException("o campo tipo não pode ser nulo");
        }
        if(veiculo.getAno() == 0){
            throw new RuntimeException("o campo ano não pode ser zero");
        }
        if(veiculo.getPlaca().length() > 7){
            throw new RuntimeException("a placa do condutor excede o máximo de caracteres (7)");
        }
        this.veiculoRepository.save(veiculo);
    }

    @Transactional
    public void atualizaVeiculo(final Long id, Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoBanco==null || !veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("não foi possivel encontrar o registro informado");
        }
        if(veiculo.getPlaca().isEmpty()){
            throw new RuntimeException("o campo placa não pode ser nulo");
        }
        if(veiculoRepository.findByPlaca(veiculo.getPlaca()) != null){
            throw new RuntimeException("o campo placa já existe");
        }
        if(veiculo.getModelo() == null){
            throw new RuntimeException("o campo modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca() == null){
            throw new RuntimeException("o campo marca do modelo não pode ser nulo");
        }
        if(veiculo.getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("o campo nome da marca do modelo não pode ser nulo");
        }
        if(veiculo.getCor() == null){
            throw new RuntimeException("o campo cor não pode ser nulo");
        }
        if(veiculo.getTipo() == null){
            throw new RuntimeException("o campo tipo não pode ser nulo");
        }
        if(veiculo.getAno() == 0){
            throw new RuntimeException("o campo ano não pode ser zero");
        }
        if(veiculo.getPlaca().length() > 7){
            throw new RuntimeException("a placa do condutor excede o máximo de caracteres (7)");
        }
        this.veiculoRepository.save(veiculo);
    }
}
