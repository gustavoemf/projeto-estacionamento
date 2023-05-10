package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.config.ValidaTelefone;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ValidaTelefone validaTelefone;

    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getVeiculo() == null){
            throw new RuntimeException("O campo veiculo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo() == null){
            throw new RuntimeException("O campo modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca() == null){
            throw new RuntimeException("O campo marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo nome da marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getCor() == null){
            throw new RuntimeException("O campo cor do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getTipo() == null){
            throw new RuntimeException("O campo tipo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getAno() == 0){
            throw new RuntimeException("O campo ano do veículo não pode ser zero");
        }
        if(movimentacao.getVeiculo().getPlaca().length() > 7){
            throw new RuntimeException("A placa do condutor excede o máximo de caracteres (7)");
        }
        if(movimentacao.getCondutor() == null){
            throw new RuntimeException("O campo condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getCpf().isEmpty()){
            throw new RuntimeException("O campo cpf do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getTelefone().isEmpty()){
            throw new RuntimeException("O campo telefone do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().length() > 100){
            throw new RuntimeException("O nome do condutor excede o máximo de caracteres (100)");
        }
        if(movimentacao.getCondutor().getCpf().length() > 15){
            throw new RuntimeException("O cpf do condutor excede o máximo de caracteres (15)");
        }
        if(movimentacao.getCondutor().getTelefone().length() > 17){
            throw new RuntimeException("O telefone do condutor excede o máximo de caracteres (17)");
        }
        if(this.validaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone()) == false){
            throw new RuntimeException("O telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getEntrada() == null){
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
        if(movimentacao.getVeiculo() == null){
            throw new RuntimeException("O campo veiculo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("O campo placa do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo() == null){
            throw new RuntimeException("O campo modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca() == null){
            throw new RuntimeException("O campo marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getModelo().getMarca().getNome().isEmpty()){
            throw new RuntimeException("O campo nome da marca do modelo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getCor() == null){
            throw new RuntimeException("O campo cor do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getTipo() == null){
            throw new RuntimeException("O campo tipo do veículo não pode ser nulo");
        }
        if(movimentacao.getVeiculo().getAno() == 0){
            throw new RuntimeException("O campo ano do veículo não pode ser zero");
        }
        if(movimentacao.getVeiculo().getPlaca().length() > 7){
            throw new RuntimeException("A placa do condutor excede o máximo de caracteres (7)");
        }
        if(movimentacao.getCondutor() == null){
            throw new RuntimeException("O campo condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().isEmpty()){
            throw new RuntimeException("O campo nome do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getCpf().isEmpty()){
            throw new RuntimeException("O campo cpf do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getTelefone().isEmpty()){
            throw new RuntimeException("O campo telefone do condutor não pode ser nulo");
        }
        if(movimentacao.getCondutor().getNome().length() > 100){
            throw new RuntimeException("O nome do condutor excede o máximo de caracteres (100)");
        }
        if(movimentacao.getCondutor().getCpf().length() > 15){
            throw new RuntimeException("O cpf do condutor excede o máximo de caracteres (15)");
        }
        if(movimentacao.getCondutor().getTelefone().length() > 17){
            throw new RuntimeException("O telefone do condutor excede o máximo de caracteres (17)");
        }
        if(this.validaTelefone.validaTelefone(movimentacao.getCondutor().getTelefone()) == false){
            throw new RuntimeException("O telefone do condutor não condiz com a formatação necessária");
        }
        if(movimentacao.getEntrada() == null){
            throw new RuntimeException("O campo entrada não pode ser nulo");
        }
        this.movimentacaoRepository.save(movimentacao);
    }
}
