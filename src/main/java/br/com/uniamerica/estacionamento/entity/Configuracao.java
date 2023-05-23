package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

@Entity
@Audited
@Table(name = "configuracoes", schema = "public")
@AuditTable(value = "configuracoes_audit", schema = "audit")
public class Configuracao extends AbstractEntity{
    @Getter @Setter
    @Column(name = "valor_hora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor_multa_minuto")
    private BigDecimal valorMinutoMulta;

    @Getter @Setter
    @Column(name = "inicio_expediente")
    private LocalTime inicioExpediente;

    @Getter @Setter
    @Column(name = "fim_expediente")
    private LocalTime fimExpediente;

    @Getter @Setter
    @Column(name = "gerar_desconto")
    private boolean gerarDesconto;

    @Getter @Setter
    @Column(name = "tempo_para_desconto")
    private LocalTime tempoParaDesconto;

    @Getter @Setter
    @Column(name = "tempo_ganho_de_desconto")
    private LocalTime tempoGanhoDeDesconto;

    @Getter @Setter
    @Column(name = "vagas_carro")
    private int vagasCarro;

    @Getter @Setter
    @Column(name = "vagas_moto")
    private int vagasMoto;

    @Getter @Setter
    @Column(name = "vagas_van")
    private int vagasVan;
}