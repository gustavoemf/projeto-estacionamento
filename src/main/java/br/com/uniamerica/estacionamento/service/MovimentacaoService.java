package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCpf;
import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ValidaTelefone validaTelefone;
    @Autowired
    private ValidaCpf validaCpf;

    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(!this.validaCpf.isCPF(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("o cpf do condutor não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone())){
            throw new RuntimeException("o telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getSaida() != null){
            LocalTime tempo = movimentacao.getSaida()
                    .minusHours(movimentacao.getEntrada().getHour())
                    .minusMinutes(movimentacao.getEntrada().getMinute())
                    .minusSeconds(movimentacao.getEntrada().getSecond());
            movimentacao.setTempo(tempo);
        }
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void atuaizaMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if(!this.validaCpf.isCPF(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("o cpf do condutor não condiz com a formatação necessária");
        }
        if(!ValidaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone())){
            throw new RuntimeException("o telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getSaida() != null){
            LocalTime tempo = movimentacao.getSaida()
                    .minusHours(movimentacao.getEntrada().getHour())
                    .minusMinutes(movimentacao.getEntrada().getMinute())
                    .minusSeconds(movimentacao.getEntrada().getSecond());
            movimentacao.setTempo(tempo);
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
