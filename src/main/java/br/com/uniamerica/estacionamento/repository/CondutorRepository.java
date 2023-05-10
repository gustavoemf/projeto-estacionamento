package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    public List<Condutor> findByAtivo(@Param("ativo") final boolean ativo);
    public Condutor findByCpf(String cpf);
}
