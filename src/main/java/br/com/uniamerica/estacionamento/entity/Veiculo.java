package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;

@Entity
@Audited
@Table(name = "veiculos", schema = "public")
@AuditTable(value = "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo placa não pode ser nulo")
    @Size(max = 7, message = "a placa do condutor excede o máximo de caracteres (7)")
    @UniqueElements(message = "o campo placa já existe")
    @Column(name = "placa", nullable = false, unique = true, length = 7)
    private String placa;

    @Getter @Setter
    @NotNull(message = "o campo modelo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo", nullable = false)
    private Modelo modelo;

    @Getter @Setter
    @NotNull(message = "o campo cor não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "cor", nullable = false)
    private Cor cor;

    @Getter @Setter
    @NotNull(message = "o campo tipo não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;

    @Getter @Setter
    @Size(min = 1, message = "o campo ano não pode ser zero")
    @Column(name = "ano", nullable = false)
    private int ano;
}