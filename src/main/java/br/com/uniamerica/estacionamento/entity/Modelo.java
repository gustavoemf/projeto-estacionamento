package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

@Entity
@Audited
@Table(name = "modelos", schema = "publico")
@AuditTable(value = "modelos_audit", schema = "audit")
public class Modelo extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo nome não pode ser nulo")
    @Length(max = 50, message = "o nome do modelo excede o máximo de caracteres (50)")
    @Column(name = "nome")
    private String nome;

    @Getter @Setter
    @NotNull(message = "o campo marca não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "marca")
    private Marca marca;
}