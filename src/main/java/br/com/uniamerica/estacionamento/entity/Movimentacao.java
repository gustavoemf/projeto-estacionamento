package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "movimentacoes", schema = "public")
@AuditTable(value = "movimentacoes_audit", schema = "audit")
public class Movimentacao extends AbstractEntity {
    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "veiculo", nullable = false)
    private Veiculo veiculo;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condutor", nullable = false)
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalTime entrada;

    @Getter @Setter
    @Column(name = "saida")
    private LocalTime saida;

    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo; //  recebe seu valor por meio da operação: entrada - saida

    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto; // recebe seu valor da variável tempoDesconto do Condutor

    @Getter @Setter
    @Column(name = "tempo_multa")
    private LocalTime tempoMulta; // soma cada minuto fora do expediente

    @Getter @Setter
    @Column(name = "valor_minuto_multa")
    private BigDecimal valorMinutoMulta; // recebe seu valor da variável valorMinutoMulta da Configuração

    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora; // recebe seu valor da variável valorHora da Configuração

    @Getter @Setter
    @Column(name = "valor_multa")
    private BigDecimal valorMulta; // valorMulta = tempoMulta x valorMinutoMulta

    @Getter @Setter
    @Column(name = "valor_normal")
    private BigDecimal valorNormal; // valorNormal = (tempo - tempoDesconto) x valorHora

    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal; // valorTotal = valorNormal + valorMulta
}