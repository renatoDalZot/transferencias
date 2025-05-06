package com.bancodbworkshop.transferencias.model;

import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
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
    private Long contaOrigem;
    @Column(name = "conta_destino")
    private Long contaDestino;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "data")
    private LocalDateTime data;

    public Transferencia(Long contaOrigem, Long contaDestino, BigDecimal valor, LocalDateTime data) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.data = data;
    }
}
