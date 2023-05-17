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
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalTime;

@Entity
@Audited
@Table(name = "condutores", schema = "publico")
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo nome não pode ser nulo")
    @Length(max = 100, message = "o nome do condutor excede o máximo de caracteres (100)")
    @Column(name = "nome")
    private String nome;

    @Getter @Setter
    @NotNull(message = "o campo cpf não pode ser nulo")
    @Length(max = 16, message = "o cpf do condutor excede o máximo de caracteres (16)")
    @UniqueElements(message = "o campo cpf já existe")
    @Column(name = "cpf")
    private String cpf;

    @Getter @Setter
    @NotNull(message = "o campo telefone não pode ser nulo")
    @Length(max = 16, message = "o telefone do condutor excede o máximo de caracteres (16)")
    @Column(name = "telefone")
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_pago")
    private LocalTime tempoPago;

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;
}