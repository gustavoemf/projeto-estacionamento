package br.com.uniamerica.estacionamento.repository;

import br.com.uniamerica.estacionamento.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbstractEntityRepository extends JpaRepository<AbstractEntity, Long> {
}
