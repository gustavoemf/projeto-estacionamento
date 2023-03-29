package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "tb_marca", schema = "publico")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
}