package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalTime;

@Entity
@Audited
@Table(name = "condutores", schema = "public")
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity {
    @Getter @Setter
    @NotNull(message = "o campo nome não pode ser nulo")
    @Size(min = 3, max = 50, message = "o nome do condutor não respeita a quantidade de caracteres necessária (3-50)")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @NotNull(message = "o campo cpf não pode ser nulo")
    @Size(max = 11, message = "o cpf do condutor não respeita a quantidade de caracteres necessária (11)")
    @UniqueElements(message = "o campo cpf já existe")
    @Column(name = "cpf", nullable = false, unique = true, length = 15)
    private String cpf;

    @Getter @Setter
    @NotNull(message = "o campo telefone não pode ser nulo")
    @Size(min = 7, max = 15, message = "o cpf do condutor não respeita a quantidade de caracteres necessária (7-15)")
    @UniqueElements(message = "o campo telefone já existe")
    @Column(name = "telefone", nullable = false, unique = true, length = 15)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_pago")
    private LocalTime tempoPago; // soma ao seu total o tempo total de cada movimentação realizada

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto; // soma ao seu total o valor de tempoGanhoDeDesconto cada vez que tempoPago atingir o valor de tempoParaDesconto da Configuração
}