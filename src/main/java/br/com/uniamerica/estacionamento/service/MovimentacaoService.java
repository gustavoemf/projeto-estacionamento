package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.CalculaTempo;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        if(movimentacao.getSaida() != null && "".equals(movimentacao.getSaida().toString())){
            throw new RuntimeException("o campo saida não pode ser vazio");
        }
        if(movimentacao.getTempo() != null){
            throw new RuntimeException("o campo tempo não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getTempoDesconto() != null){
            throw new RuntimeException("o campo tempoDesconto não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getTempoMulta() != null){
            throw new RuntimeException("o campo tempoMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorMinutoMulta() != null){
            throw new RuntimeException("o campo valorMinutoMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorHora() != null){
            throw new RuntimeException("o campo valorHora não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorMulta() != null){
            throw new RuntimeException("o campo valorMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorNormal() != null){
            throw new RuntimeException("o campo valorNormal não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorTotal() != null){
            throw new RuntimeException("o campo valorTotal não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        movimentacao.setTempo(LocalTime.of(0, 0, 0));
        movimentacao.setTempoDesconto(LocalTime.of(0, 0, 0));
        movimentacao.setTempoMulta(LocalTime.of(0, 0, 0));
        movimentacao.setValorTotal(BigDecimal.ZERO);
        movimentacao.setValorNormal(BigDecimal.ZERO);
        movimentacao.setValorMulta(BigDecimal.ZERO);
        if (movimentacao.getSaida() != null){
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            if (configuracaoRepository.findGerarDesconto() && condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto() != null){
                movimentacao.setTempoDesconto(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto());
                movimentacao.setTempo(CalculaTempo.calculaTempoComDesconto(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()), condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto()));
            } else {
                movimentacao.setTempo(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()));
            }
            if (CalculaTempo.validaTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), configuracaoRepository.findTempoParaDesconto())) {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(CalculaTempo.calculaTempoGanhoDeDesconto(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto(), configuracaoRepository.findTempoGanhoDeDesconto()));
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoDesconto(CalculaTempo.subtraiTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), configuracaoRepository.findTempoParaDesconto()));
            } else {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(CalculaTempo.calculaTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), movimentacao.getTempo()));
            }
            if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente()));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente()));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                LocalTime tempoMultaInicio = CalculaTempo.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
                LocalTime tempoMultaFim = CalculaTempo.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaTotal(tempoMultaInicio, tempoMultaFim));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            }
            movimentacao.setValorNormal(CalculaTempo.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora()));
            movimentacao.setValorTotal(CalculaTempo.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal()));
        }
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
        if(movimentacao.getSaida() != null && "".equals(movimentacao.getSaida().toString())){
            throw new RuntimeException("o campo saida não pode ser vazio");
        }
        if(movimentacao.getTempo() != null){
            throw new RuntimeException("o campo tempo não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getTempoDesconto() != null){
            throw new RuntimeException("o campo tempoDesconto não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getTempoMulta() != null){
            throw new RuntimeException("o campo tempoMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorMinutoMulta() != null){
            throw new RuntimeException("o campo valorMinutoMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorHora() != null){
            throw new RuntimeException("o campo valorHora não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorMulta() != null){
            throw new RuntimeException("o campo valorMulta não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorNormal() != null){
            throw new RuntimeException("o campo valorNormal não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        if(movimentacao.getValorTotal() != null){
            throw new RuntimeException("o campo valorTotal não pode ser inserido pois é gerado de acordo com a entrada e saida");
        }
        movimentacao.setTempo(LocalTime.of(0, 0, 0));
        movimentacao.setTempoDesconto(LocalTime.of(0, 0, 0));
        movimentacao.setTempoMulta(LocalTime.of(0, 0, 0));
        movimentacao.setValorTotal(BigDecimal.ZERO);
        movimentacao.setValorNormal(BigDecimal.ZERO);
        movimentacao.setValorMulta(BigDecimal.ZERO);
        if (movimentacao.getSaida() != null){
            movimentacao.setValorHora(configuracaoRepository.findValorHora());
            movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
            if (configuracaoRepository.findGerarDesconto() && condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto() != null){
                movimentacao.setTempoDesconto(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto());
                movimentacao.setTempo(CalculaTempo.calculaTempoComDesconto(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()), condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto()));
            } else {
                movimentacao.setTempo(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()));
            }
            if (CalculaTempo.validaTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), configuracaoRepository.findTempoParaDesconto())) {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(CalculaTempo.calculaTempoGanhoDeDesconto(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto(), configuracaoRepository.findTempoGanhoDeDesconto()));
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoDesconto(CalculaTempo.subtraiTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), configuracaoRepository.findTempoParaDesconto()));
            } else {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(CalculaTempo.calculaTempoPago(condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago(), movimentacao.getTempo()));
            }
            if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente()));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente()));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                LocalTime tempoMultaInicio = CalculaTempo.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
                LocalTime tempoMultaFim = CalculaTempo.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
                movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaTotal(tempoMultaInicio, tempoMultaFim));
                movimentacao.setValorMulta(CalculaTempo.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
            }
            movimentacao.setValorNormal(CalculaTempo.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora()));
            movimentacao.setValorTotal(CalculaTempo.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal()));
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
