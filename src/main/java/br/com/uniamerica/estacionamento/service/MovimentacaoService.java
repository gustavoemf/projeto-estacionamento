package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaCpf;
import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private ValidaTelefone validaTelefone;
    @Autowired
    private ValidaCpf validaCpf;

    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
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
        if(movimentacao.getTempo() != null){
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            movimentacao.setValorTotal(configuracaoRepository.findValorHora()
                    .multiply(new BigDecimal(movimentacao.getTempo().getHour()))
            );
        }
        if(movimentacao.getSaida() != null){
            LocalTime tempo = movimentacao.getSaida()
                    .minusHours(movimentacao.getEntrada().getHour())
                    .minusMinutes(movimentacao.getEntrada().getMinute())
                    .minusSeconds(movimentacao.getEntrada().getSecond());
            movimentacao.setTempo(tempo);
        }
        if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
            Duration tempoMulta = Duration.between(configuracaoRepository.findInicioExpediente(), movimentacao.getEntrada());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(tempoMulta.toMinutes());
        }
        if (movimentacao.getSaida() != null && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            Duration tempoMulta = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(movimentacao.getTempoMulta().longValue() + tempoMulta.toMinutes());
        }
        if(movimentacao.getTempo()!=null) {
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            BigDecimal valorTotal = movimentacao.getValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorTotal(valorTotal);
        }
        if(movimentacao.getCadastro() == null){
            movimentacao.setCadastro(LocalDateTime.now());
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
        if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
            Duration tempoMulta = Duration.between(configuracaoRepository.findInicioExpediente(), movimentacao.getEntrada());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(tempoMulta.toMinutes());
        }
        if (movimentacao.getSaida() != null && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            Duration tempoMulta = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(movimentacao.getTempoMulta().longValue() + tempoMulta.toMinutes());
        }
        if(movimentacao.getTempo()!=null) {
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            BigDecimal valorTotal = movimentacao.getValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorTotal(valorTotal);
        }
        if(movimentacao.getAtualizacao() == null){
            movimentacao.setAtualizacao(LocalDateTime.now());
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
