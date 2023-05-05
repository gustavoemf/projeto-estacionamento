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
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if("".equals(veiculo.getModelo().getNome())){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if("".equals(veiculo.getModelo().getMarca().getNome())){
            throw new RuntimeException("O campo mara não pode ser nulo");
        }
        if("".equals(veiculo.getAno())){
            throw new RuntimeException("O campo ano não pode ser nulo");
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
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if("".equals(veiculo.getModelo().getNome())){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if("".equals(veiculo.getModelo().getMarca().getNome())){
            throw new RuntimeException("O campo marca não pode ser nulo");
        }
        if("".equals(veiculo.getAno())){
            throw new RuntimeException("O campo ano não pode ser nulo");
        }
        this.veiculoRepository.save(veiculo);
    }
}
