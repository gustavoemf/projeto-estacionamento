package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    List<Condutor> findByAtivo(@Param("ativo") final boolean ativo);
    Condutor findByCpf(String cpf);
    Condutor findByTelefone(String telefone);
    @Query("SELECT x.tempoPago FROM Condutor x WHERE x.tempoPago IS NOT NULL")
    LocalTime findTempoPago();
    @Query("SELECT x.tempoDesconto FROM Condutor x WHERE x.tempoDesconto IS NOT NULL")
    LocalTime findTempoDesconto();
    @Query("SELECT x.tempoDesconto FROM Condutor x WHERE x.tempoDesconto IS NOT NULL")
    LocalTime setTempoPago();
    @Query("SELECT x.tempoDesconto FROM Condutor x WHERE x.tempoDesconto IS NOT NULL")
    LocalTime setTempoDesconto();
}
