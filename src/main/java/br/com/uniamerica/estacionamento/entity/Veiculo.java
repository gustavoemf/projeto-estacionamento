package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Audited
@Table(name = "veiculos", schema = "publico")
@AuditTable(value = "veiculos_audit", schema = "audit")
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo placa não pode ser nulo")
    @Length(max = 7, message = "a placa do condutor excede o máximo de caracteres (7)")
    @UniqueElements(message = "o campo placa já existe")
    @Column(name = "placa")
    private String placa;
    @Getter @Setter
    @NotNull(message = "o campo modelo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "modelo")
    private Modelo modelo;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @NotNull(message = "o campo cor não pode ser nulo")
    @Column(name = "cor", nullable = false)
    private Cor cor;
    @Enumerated(EnumType.STRING)
    @Getter @Setter
    @NotNull(message = "o campo tipo não pode ser nulo")
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano", nullable = false)
    private int ano;
}