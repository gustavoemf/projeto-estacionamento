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
        movimentacao.setValorHora(configuracaoRepository.findValorHora());
        movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
        if(movimentacao.getCondutor().getTempoDesconto() != null || movimentacao.getCondutor().getTempoDesconto().isBefore(configuracaoRepository.findTempoGanhoDeDesconto())){
            movimentacao.setTempo(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()));
        } else {
            movimentacao.setTempoDesconto(movimentacao.getCondutor().getTempoDesconto());
            movimentacao.setTempo(CalculaTempo.calculaTempoComDesconto(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()), configuracaoRepository.findTempoGanhoDeDesconto()));
        }
        movimentacao.getCondutor().setTempoPago(CalculaTempo.calculaTempoPago(movimentacao.getCondutor().getTempoPago(), movimentacao.getTempo()));
        if(CalculaTempo.validaTempoPago(movimentacao.getCondutor().getTempoPago(), configuracaoRepository.findTempoParaDesconto())){
            movimentacao.getCondutor().setTempoDesconto(CalculaTempo.calculaTempoGanhoDeDesconto(movimentacao.getCondutor().getTempoDesconto(), configuracaoRepository.findTempoGanhoDeDesconto()));
            movimentacao.getCondutor().setTempoPago(CalculaTempo.subtraiTempoPago(movimentacao.getCondutor().getTempoPago(), configuracaoRepository.findTempoParaDesconto()));
        }
        if(movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())){
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaInicio(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente()));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaInicio(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaSaida(movimentacao.getSaida(), configuracaoRepository.findFimExpediente()));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaSaida(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            LocalTime tempoMultaInicio = CalculaTempo.calculaTempoMultaInicio(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
            LocalTime tempoMultaFim = CalculaTempo.calculaTempoMultaSaida(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaTotal(tempoMultaInicio, tempoMultaFim));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaTotal(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        }
        movimentacao.setValorNormal(CalculaTempo.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora()));
        movimentacao.setValorTotal(CalculaTempo.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal()));
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
        movimentacao.setValorHora(configuracaoRepository.findValorHora());
        movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());
        if(movimentacao.getCondutor().getTempoDesconto() != null || movimentacao.getCondutor().getTempoDesconto().isBefore(configuracaoRepository.findTempoGanhoDeDesconto())){
            movimentacao.setTempo(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()));
        } else {
            movimentacao.setTempoDesconto(movimentacao.getCondutor().getTempoDesconto());
            movimentacao.setTempo(CalculaTempo.calculaTempoComDesconto(CalculaTempo.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida()), configuracaoRepository.findTempoGanhoDeDesconto()));
        }
        movimentacao.getCondutor().setTempoPago(CalculaTempo.calculaTempoPago(movimentacao.getCondutor().getTempoPago(), movimentacao.getTempo()));
        if(CalculaTempo.validaTempoPago(movimentacao.getCondutor().getTempoPago(), configuracaoRepository.findTempoParaDesconto())){
            movimentacao.getCondutor().setTempoDesconto(CalculaTempo.calculaTempoGanhoDeDesconto(movimentacao.getCondutor().getTempoDesconto(), configuracaoRepository.findTempoGanhoDeDesconto()));
            movimentacao.getCondutor().setTempoPago(CalculaTempo.subtraiTempoPago(movimentacao.getCondutor().getTempoPago(), configuracaoRepository.findTempoParaDesconto()));
        }
        if(movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())){
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaInicio(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente()));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaInicio(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaSaida(movimentacao.getSaida(), configuracaoRepository.findFimExpediente()));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaSaida(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
            LocalTime tempoMultaInicio = CalculaTempo.calculaTempoMultaInicio(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
            LocalTime tempoMultaFim = CalculaTempo.calculaTempoMultaSaida(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
            movimentacao.setTempoMulta(CalculaTempo.calculaTempoMultaTotal(tempoMultaInicio, tempoMultaFim));
            movimentacao.setValorMulta(CalculaTempo.calculaValorMultaTotal(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta()));
        }
        movimentacao.setValorNormal(CalculaTempo.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora()));
        movimentacao.setValorTotal(CalculaTempo.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal()));
        if(movimentacao.getCadastro() != null){
            throw new RuntimeException("é impossível alterar a data de cadastro");
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
