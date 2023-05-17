package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(min = 3, max = 50, message = "o nome do modelo não respeita a quantidade de caracteres necessária (3-50)")
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @Getter @Setter
    @NotNull(message = "o campo marca não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "marca", nullable = false)
    private Marca marca;
}