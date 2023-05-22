package br.com.uniamerica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@MappedSuperclass
public abstract class AbstractEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Getter @Setter
    @Column(name = "cadastro")
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "atualizacao")
    private LocalDateTime atualizacao;
    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    @PrePersist
    public void prePersist(){
        this.cadastro = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.atualizacao = LocalDateTime.now();
    }
}