package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Audited
@Table(name = "marcas", schema = "public")
@AuditTable(value = "marca_audit", schema = "audit")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo nome não pode ser nulo")
    @Size(min = 3, max = 50, message = "o nome da marca excede o máximo de caracteres (50)")
    @UniqueElements(message = "o campo nome já existe")
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;
}