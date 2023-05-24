package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "modelos", schema = "public")
@AuditTable(value = "modelos_audit", schema = "audit")
public class Modelo extends AbstractEntity {
    @Getter @Setter
    @Size(min = 2, max = 50, message = "o nome do modelo não respeita a quantidade de caracteres necessária (2-50)")
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "marca", nullable = false)
    private Marca marca;
}