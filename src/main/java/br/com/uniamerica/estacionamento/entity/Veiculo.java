package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_veiculo", schema = "publico")
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @Column(name = "placa", nullable = false, unique = true, length = 7)
    private String placa;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "cor", nullable = false, length = 10)
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @Column(name = "tipo", nullable = false, length = 10)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private int ano;
}