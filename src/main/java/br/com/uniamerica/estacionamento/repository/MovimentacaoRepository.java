package br.com.uniamerica.estacionamento.repository;
import br.com.uniamerica.estacionamento.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    @Query("SELECT m FROM Movimentacao m WHERE m.saida IS NULL")
    List<Movimentacao> findByAbertas();
}