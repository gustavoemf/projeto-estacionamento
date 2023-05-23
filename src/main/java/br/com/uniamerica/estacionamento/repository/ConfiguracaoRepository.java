package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
    @Query("SELECT x FROM Configuracao x WHERE x.ativo = true")
    List<Configuracao> findByAtivo();
    @Query("SELECT vh.valorHora FROM Configuracao vh WHERE vh.valorHora IS NOT NULL")
    BigDecimal findValorHora();
    @Query("SELECT vh.valorMinutoMulta FROM Configuracao vh WHERE vh.valorMinutoMulta IS NOT NULL")
    BigDecimal findValorMultaMinuto();
    @Query("SELECT x.inicioExpediente FROM Configuracao x where x.inicioExpediente IS NOT NULL")
    LocalTime findInicioExpediente();
    @Query("SELECT x.fimExpediente FROM Configuracao x where x.fimExpediente IS NOT NULL")
    LocalTime findFimExpediente();
    @Query("SELECT vh.tempoGanhoDeDesconto FROM Configuracao vh WHERE vh.tempoGanhoDeDesconto IS NOT NULL")
    LocalTime findTempoGanhoDeDesconto();
    @Query("SELECT vh.tempoParaDesconto FROM Configuracao vh WHERE vh.tempoParaDesconto IS NOT NULL")
    LocalTime findTempoParaDesconto();
}
