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
        if(movimentacao.getVeiculo() == null){
            throw new RuntimeException("o campo veiculo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("o campo placa do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo() == null){
            throw new RuntimeException("o campo modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca() == null){
            throw new RuntimeException("o campo marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("o campo nome da marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getCor() == null){
            throw new RuntimeException("o campo cor do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getTipo() == null){
            throw new RuntimeException("o campo tipo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getAno() == 0){
            throw new RuntimeException("o campo ano do veículo não pode ser zero");
        }
        if(movimentacao.getVeiculo().getPlaca().length() > 7){
            throw new RuntimeException("a placa do condutor excede o máximo de caracteres (7)");
        }
        if(movimentacao.getCondutor() == null){
            throw new RuntimeException("o campo condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getCpf().isEmpty()){
            throw new RuntimeException("o campo cpf do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getTelefone().isEmpty()){
            throw new RuntimeException("o campo telefone do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().length() > 100){
            throw new RuntimeException("o nome do condutor excede o máximo de caracteres (100)");
        }
        if(movimentacao.getCondutor().getCpf().length() > 15){
            throw new RuntimeException("o cpf do condutor excede o máximo de caracteres (15)");
        }
        if(this.validaCpf.isCPF(movimentacao.getCondutor().getCpf()) == false){
            throw new RuntimeException("o cpf do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getCondutor().getTelefone().length() > 17){
            throw new RuntimeException("o telefone do condutor excede o máximo de caracteres (17)");
        }
        if(this.validaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone()) == false){
            throw new RuntimeException("o telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getEntrada() == null){
            throw new RuntimeException("o campo entrada não pode ser nulo");
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
        if(movimentacao.getVeiculo() == null){
            throw new RuntimeException("o campo veiculo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("o campo placa do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo() == null){
            throw new RuntimeException("o campo modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca() == null){
            throw new RuntimeException("o campo marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("o campo nome da marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getCor() == null){
            throw new RuntimeException("o campo cor do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getTipo() == null){
            throw new RuntimeException("o campo tipo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getAno() == 0){
            throw new RuntimeException("o campo ano do veículo não pode ser zero");
        }
        if(movimentacao.getVeiculo().getPlaca().length() > 7){
            throw new RuntimeException("a placa do condutor excede o máximo de caracteres (7)");
        }
        if(movimentacao.getCondutor() == null){
            throw new RuntimeException("o campo condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().isEmpty()){
            throw new RuntimeException("o campo nome do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getCpf().isEmpty()){
            throw new RuntimeException("o campo cpf do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getTelefone().isEmpty()){
            throw new RuntimeException("o campo telefone do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().length() > 100){
            throw new RuntimeException("o nome do condutor excede o máximo de caracteres (100)");
        }
        if(movimentacao.getCondutor().getCpf().length() > 15){
            throw new RuntimeException("o cpf do condutor excede o máximo de caracteres (15)");
        }
        if(this.validaCpf.isCPF(movimentacao.getCondutor().getCpf()) == false){
            throw new RuntimeException("o cpf do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getCondutor().getTelefone().length() > 17){
            throw new RuntimeException("o telefone do condutor excede o máximo de caracteres (17)");
        }
        if(this.validaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone()) == false){
            throw new RuntimeException("o telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getEntrada() == null){
            throw new RuntimeException("o campo entrada não pode ser nulo");
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
