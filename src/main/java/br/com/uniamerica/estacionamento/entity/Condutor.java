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

import java.time.LocalTime;

@Entity
@Audited
@Table(name = "condutores", schema = "public")
@AuditTable(value = "condutores_audit", schema = "audit")
public class Condutor extends AbstractEntity {
    @Getter @Setter
    @Size(min = 2, max = 50, message = "o nome do condutor não respeita a quantidade de caracteres necessária (2-50)")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Getter @Setter
    @Column(name = "telefone", nullable = false, length = 15)
    private String telefone;

    @Getter @Setter
    @Column(name = "tempo_pago")
    private LocalTime tempoPago; // soma ao seu total o tempo total de cada movimentação realizada

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto; // soma ao seu total o valor de tempoGanhoDeDesconto cada vez que tempoPago atingir o valor de tempoParaDesconto da Configuração
}