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

@Entity
@Audited
@Table(name = "marcas", schema = "public")
@AuditTable(value = "marcas_audit", schema = "audit")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Size(min = 2, max = 50, message = "o nome da marca não respeita a quantidade de caracteres necessária (2-50)")
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;
}