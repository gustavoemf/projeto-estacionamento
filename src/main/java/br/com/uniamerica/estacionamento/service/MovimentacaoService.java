package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.Calculos;
import br.com.uniamerica.estacionamento.config.Validacoes;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.CondutorRepository;
import br.com.uniamerica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        if(configuracaoRepository.findInicioExpediente() == null
                || configuracaoRepository.findFimExpediente() == null
                || configuracaoRepository.findValorHora() == null
                || configuracaoRepository.findValorMultaMinuto() == null
                || configuracaoRepository.findTempoGanhoDeDesconto() == null
                || configuracaoRepository.findTempoParaDesconto() == null){
            throw new RuntimeException("verifique se os seguintes campos não estão nulos na entidade configuração: InicioExpediente, FimExpediente, ValorHora, ValorMultaMinuto, TempoGanhoDeDesconto, TempoParaDesconto");
        }
        if(movimentacao.getId() != null){
            throw new RuntimeException("o campo id não deve ser inserido");
        }
        if(movimentacao.getVeiculo() == null){
            throw new RuntimeException("o campo veiculo não pode ser nulo");
        }
        if(movimentacao.getCondutor() == null){
            throw new RuntimeException("o campo condutor não pode ser nulo");
        }
        if(movimentacao.getEntrada() == null){
            throw new RuntimeException("o campo entrada não pode ser nulo");
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

        movimentacao.setTempo(LocalTime.of(0, 0, 0));
        movimentacao.setTempoDesconto(LocalTime.of(0, 0, 0));
        movimentacao.setTempoMulta(LocalTime.of(0, 0, 0));
        movimentacao.setValorTotal(BigDecimal.ZERO);
        movimentacao.setValorNormal(BigDecimal.ZERO);
        movimentacao.setValorMulta(BigDecimal.ZERO);

        movimentacao.setValorHora(configuracaoRepository.findValorHora());
        movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());

        if (movimentacao.getSaida() != null){
            LocalTime condutorTempoDescontoBanco = condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto();
            LocalTime condutorTempoPagoBanco = condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago();
            LocalTime calculaTempo = Calculos.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida());
            LocalTime calculaTempoComDesconto = Calculos.calculaTempoComDesconto(calculaTempo, condutorTempoDescontoBanco);
            boolean validaTempoPago = Validacoes.validaTempoPago(condutorTempoPagoBanco, configuracaoRepository.findTempoParaDesconto());
            LocalTime calculaTempoGanhoDeDesconto = Calculos.calculaTempoGanhoDeDesconto(condutorTempoDescontoBanco, configuracaoRepository.findTempoGanhoDeDesconto());
            LocalTime subtraiTempoPago = Calculos.subtraiTempoPago(condutorTempoPagoBanco, configuracaoRepository.findTempoParaDesconto());
            LocalTime calculaTempoPago = Calculos.calculaTempoPago(condutorTempoPagoBanco, movimentacao.getTempo());
            LocalTime calculaTempoMultaEntrada = Calculos.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
            LocalTime calculaTempoMultaSaida = Calculos.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
            LocalTime calculaTempoMultaTotal = Calculos.calculaTempoMultaTotal(calculaTempoMultaEntrada, calculaTempoMultaSaida);
            BigDecimal calculaValorMulta = Calculos.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta());
            BigDecimal calculaValorNormal = Calculos.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora());
            BigDecimal calculaValorTotal = Calculos.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal());

            if (configuracaoRepository.findGerarDesconto() && condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto() != null){
                movimentacao.setTempoDesconto(condutorTempoDescontoBanco);
                movimentacao.setTempo(calculaTempoComDesconto);
            } else {
                movimentacao.setTempo(calculaTempo);
            }
            if (validaTempoPago) {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(calculaTempoGanhoDeDesconto);
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoDesconto(subtraiTempoPago);
            } else {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(calculaTempoPago);
            }
            if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaEntrada);
                movimentacao.setValorMulta(calculaValorMulta);
            } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaSaida);
                movimentacao.setValorMulta(calculaValorMulta);
            } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaTotal);
                movimentacao.setValorMulta(calculaValorMulta);
            }
            movimentacao.setValorNormal(calculaValorNormal);
            BigDecimal valorNormal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorNormal(valorNormal);
            if (movimentacao.getTempoMulta() != null) {
                movimentacao.setValorTotal(calculaValorTotal);
                BigDecimal valorTotal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
                movimentacao.setValorTotal(valorTotal);
            } else {
                movimentacao.setValorTotal(movimentacao.getValorNormal());
                BigDecimal valorTotal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
                movimentacao.setValorTotal(valorTotal);
            }
        }
        this.movimentacaoRepository.save(movimentacao);
    }
    @Transactional
    public void atuaizaMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("não foi possível identificar o registro informado");
        }
        if(configuracaoRepository.findInicioExpediente() == null
                || configuracaoRepository.findFimExpediente() == null
                || configuracaoRepository.findValorHora() == null
                || configuracaoRepository.findValorMultaMinuto() == null
                || configuracaoRepository.findTempoGanhoDeDesconto() == null
                || configuracaoRepository.findTempoParaDesconto() == null){
            throw new RuntimeException("verifique se os seguintes campos não estão nulos na entidade configuração: InicioExpediente, FimExpediente, ValorHora, ValorMultaMinuto, TempoGanhoDeDesconto, TempoParaDesconto");
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

        boolean movimentacaoAtivoBanco = movimentacaoRepository.findById(movimentacao.getId()).get().isAtivo();
        LocalDateTime movimentacaoCadastroBanco = movimentacaoRepository.findById(movimentacao.getId()).get().getCadastro();

        movimentacao.setTempo(LocalTime.of(0, 0, 0));
        movimentacao.setTempoDesconto(LocalTime.of(0, 0, 0));
        movimentacao.setTempoMulta(LocalTime.of(0, 0, 0));
        movimentacao.setValorTotal(BigDecimal.ZERO);
        movimentacao.setValorNormal(BigDecimal.ZERO);
        movimentacao.setValorMulta(BigDecimal.ZERO);

        movimentacao.setValorHora(configuracaoRepository.findValorHora());
        movimentacao.setValorMinutoMulta(configuracaoRepository.findValorMultaMinuto());

        if (movimentacao.getSaida() != null){
            LocalTime condutorTempoDescontoBanco = condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto();
            LocalTime condutorTempoPagoBanco = condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoPago();
            LocalTime calculaTempo = Calculos.calculaTempo(movimentacao.getEntrada(), movimentacao.getSaida());
            LocalTime calculaTempoComDesconto = Calculos.calculaTempoComDesconto(calculaTempo, condutorTempoDescontoBanco);
            boolean validaTempoPago = Validacoes.validaTempoPago(condutorTempoPagoBanco, configuracaoRepository.findTempoParaDesconto());
            LocalTime calculaTempoGanhoDeDesconto = Calculos.calculaTempoGanhoDeDesconto(condutorTempoDescontoBanco, configuracaoRepository.findTempoGanhoDeDesconto());
            LocalTime subtraiTempoPago = Calculos.subtraiTempoPago(condutorTempoPagoBanco, configuracaoRepository.findTempoParaDesconto());
            LocalTime calculaTempoPago = Calculos.calculaTempoPago(condutorTempoPagoBanco, movimentacao.getTempo());
            LocalTime calculaTempoMultaEntrada = Calculos.calculaTempoMulta(movimentacao.getEntrada(), configuracaoRepository.findInicioExpediente());
            LocalTime calculaTempoMultaSaida = Calculos.calculaTempoMulta(movimentacao.getSaida(), configuracaoRepository.findFimExpediente());
            LocalTime calculaTempoMultaTotal = Calculos.calculaTempoMultaTotal(calculaTempoMultaEntrada, calculaTempoMultaSaida);
            BigDecimal calculaValorMulta = Calculos.calculaValorMulta(movimentacao.getTempoMulta(), movimentacao.getValorMinutoMulta());
            BigDecimal calculaValorNormal = Calculos.calculaValorNormal(movimentacao.getTempo(), movimentacao.getValorHora());
            BigDecimal calculaValorTotal = Calculos.calculaValorTotal(movimentacao.getValorMulta(), movimentacao.getValorNormal());

            if (configuracaoRepository.findGerarDesconto() && condutorRepository.findById(movimentacao.getCondutor().getId()).get().getTempoDesconto() != null){
                movimentacao.setTempoDesconto(condutorTempoDescontoBanco);
                movimentacao.setTempo(calculaTempoComDesconto);
            } else {
                movimentacao.setTempo(calculaTempo);
            }
            if (validaTempoPago) {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(calculaTempoGanhoDeDesconto);
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoDesconto(subtraiTempoPago);
            } else {
                condutorRepository.findById(movimentacao.getCondutor().getId()).get().setTempoPago(calculaTempoPago);
            }
            if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaEntrada);
                movimentacao.setValorMulta(calculaValorMulta);
            } else if (movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaSaida);
                movimentacao.setValorMulta(calculaValorMulta);
            } else if (movimentacao.getEntrada().isBefore(configuracaoRepository.findInicioExpediente()) && movimentacao.getSaida().isAfter(configuracaoRepository.findFimExpediente())) {
                movimentacao.setTempoMulta(calculaTempoMultaTotal);
                movimentacao.setValorMulta(calculaValorMulta);
            }
            movimentacao.setValorNormal(calculaValorNormal);
            BigDecimal valorNormal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
            movimentacao.setValorNormal(valorNormal);
            if (movimentacao.getTempoMulta() != null) {
                movimentacao.setValorTotal(calculaValorTotal);
                BigDecimal valorTotal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
                movimentacao.setValorTotal(valorTotal);
            } else {
                movimentacao.setValorTotal(movimentacao.getValorNormal());
                BigDecimal valorTotal = configuracaoRepository.findValorHora().multiply(new BigDecimal(movimentacao.getTempo().getHour()));
                movimentacao.setValorTotal(valorTotal);
            }
        }
        if(movimentacao.getCadastro() == null){
            movimentacao.setCadastro(movimentacaoCadastroBanco);
        }
        /*if(movimentacao.getCadastro() != movimentacaoCadastroBanco){
            throw new RuntimeException("o cadastro não pode ser alterado");
        }*/
        this.movimentacaoRepository.save(movimentacao);
    }
}