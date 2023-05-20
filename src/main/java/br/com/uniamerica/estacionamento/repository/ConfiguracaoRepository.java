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
    @Query("SELECT vh.valorMultaMinuto FROM Configuracao vh WHERE vh.valorMultaMinuto IS NOT NULL")
    BigDecimal findValorMultaMinuto();
    @Query("SELECT x.inicioExpediente FROM Configuracao x where x.inicioExpediente IS NOT NULL")
    LocalTime findInicioExpediente();
    @Query("SELECT x.fimExpediente FROM Configuracao x where x.fimExpediente IS NOT NULL")
    LocalTime findFimExpediente();
}
