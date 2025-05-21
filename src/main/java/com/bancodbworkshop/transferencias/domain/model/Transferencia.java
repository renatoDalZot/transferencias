package com.bancodbworkshop.transferencias.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "transferencia")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "conta_origem")
    private Long contaOrigemId;
    @Column(name = "conta_destino")
    private Long contaDestinoId;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "data")
    private LocalDateTime data;

    public Transferencia(Long contaOrigemId, Long contaDestinoId, BigDecimal valor, LocalDateTime data) {
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
        this.data = data;
    }
}
