package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Audited
@Table(name = "marcas", schema = "publico")
@AuditTable(value = "marca_audit", schema = "audit")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
}