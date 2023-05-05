package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome()==null || movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome()==null || movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo marca não pode ser nulo");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("O campo ano não pode ser nulo");
        }
        if("".equals(movimentacao.getCondutor().getNome())){
            throw new RuntimeException("O campo condutor não pode ser nulo");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("O campo cpf não pode ser nulo");
        }
        if("".equals(movimentacao.getEntrada())){
            throw new RuntimeException("O campo entrada não pode ser nulo");
        }
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void atuaizaMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("Não foi possível identificar o registro informado");
        }
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome()==null || movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo modelo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome()==null || movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo marca não pode ser nulo");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("O campo ano não pode ser nulo");
        }
        if("".equals(movimentacao.getCondutor().getNome())){
            throw new RuntimeException("O campo nome não pode ser nulo");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("O campo cpf não pode ser nulo");
        }
        if("".equals(movimentacao.getEntrada())){
            throw new RuntimeException("O campo entrada não pode ser nulo");
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
