package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if(veiculoRepository.findById(movimentacao.getVeiculo().getId()).isEmpty()){
            throw new RuntimeException("o id do veiculo inserido não existe");
        }
        if(condutorRepository.findById(movimentacao.getCondutor().getId()).isEmpty()){
            throw new RuntimeException("o id do condutor inserido não existe");
        }
        if("".equals(movimentacao.getEntrada().toString())){
            throw new RuntimeException("o campo entrada não pode ser vazio");
        }
        if("".equals(movimentacao.getSaida().toString())){
            throw new RuntimeException("o campo saida não pode ser vazio");
        }
        if("".equals(movimentacao.getTempo().toString())){
            throw new RuntimeException("o campo tempo não pode ser vazio");
        }
        if("".equals(movimentacao.getTempoDesconto().toString())){
            throw new RuntimeException("o campo tempoDesconto não pode ser vazio");
        }
        if("".equals(movimentacao.getTempoMulta().toString())){
            throw new RuntimeException("o campo tempoMulta não pode ser vazio");
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
        if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
            Duration tempoMulta = Duration.between(configuracaoRepository.findInicioExpediente(), movimentacao.getEntrada());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(tempoMulta.toMinutes());
        }
        if (movimentacao.getSaida() != null && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            Duration tempoMulta = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(movimentacao.getTempoMulta() + tempoMulta.toMinutes());
        }
        if(movimentacao.getTempo()!=null) {
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            BigDecimal valorTotal = movimentacao.getValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorTotal(valorTotal);
        }
        movimentacao.setAtivo(true);
        this.movimentacaoRepository.save(movimentacao);
    }
    @Transactional
    public void atuaizaMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if(veiculoRepository.findById(movimentacao.getVeiculo().getId()).isEmpty()){
            throw new RuntimeException("o id do veiculo inserido não existe");
        }
        if(condutorRepository.findById(movimentacao.getCondutor().getId()).isEmpty()){
            throw new RuntimeException("o id do condutor inserido não existe");
        }
        if("".equals(movimentacao.getEntrada().toString())){
            throw new RuntimeException("o campo entrada não pode ser vazio");
        }
        if("".equals(movimentacao.getSaida().toString())){
            throw new RuntimeException("o campo saida não pode ser vazio");
        }
        if("".equals(movimentacao.getTempo().toString())){
            throw new RuntimeException("o campo tempo não pode ser vazio");
        }
        if("".equals(movimentacao.getTempoDesconto().toString())){
            throw new RuntimeException("o campo tempoDesconto não pode ser vazio");
        }
        if("".equals(movimentacao.getTempoMulta().toString())){
            throw new RuntimeException("o campo tempoMulta não pode ser vazio");
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
        if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
            Duration tempoMulta = Duration.between(configuracaoRepository.findInicioExpediente(), movimentacao.getEntrada());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(tempoMulta.toMinutes());
        }
        if (movimentacao.getSaida() != null && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            Duration tempoMulta = Duration.between(movimentacao.getEntrada(), movimentacao.getSaida());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            movimentacao.setTempoMulta(movimentacao.getTempoMulta() + tempoMulta.toMinutes());
        }
        if(movimentacao.getTempo()!=null) {
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            BigDecimal valorTotal = movimentacao.getValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorTotal(valorTotal);
        }
        if(movimentacao.getCadastro() != null){
            throw new RuntimeException("é impossível alterar a data de cadastro");
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
