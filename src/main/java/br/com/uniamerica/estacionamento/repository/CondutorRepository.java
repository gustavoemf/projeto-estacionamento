package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    Optional<Condutor> findById(Long id);
    List<Condutor> findByAtivo(@Param("ativo") final boolean ativo);
    Condutor findByCpf(String cpf);
    Condutor findByTelefone(String telefone);
}
