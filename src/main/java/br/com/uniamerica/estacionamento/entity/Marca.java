package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

@Entity
@Audited
@Table(name = "marcas", schema = "publico")
@AuditTable(value = "marca_audit", schema = "audit")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo nome não pode ser nulo")
    @Length(max = 50, message = "o nome da marca excede o máximo de caracteres (50)")
    @Column(name = "nome")
    private String nome;
}