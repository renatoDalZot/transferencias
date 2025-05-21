package com.bancodbworkshop.transferencias.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "conta")
@Getter
@NoArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "agencia")
    private Integer agencia;
    @Column(name = "titular")
    @Transient
    private String titular;
    @Column(name = "saldo")
    private BigDecimal saldo;

    public Conta(Integer agencia, String titular, BigDecimal saldo) {
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = saldo;
    }

    public void debitar(BigDecimal valor) {
        if (eMaiorQueOSaldo(valor)) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência");
        }
        if (eMenorOuIgualAZero(valor)) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (eMenorOuIgualAZero(valor)) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }
        this.saldo = this.saldo.add(valor);
    }

    private boolean eMenorOuIgualAZero(BigDecimal valor) {
        return valor.compareTo(BigDecimal.ZERO) <= 0;
    }

    private boolean eMaiorQueOSaldo(BigDecimal valor) {
        return valor.compareTo(this.saldo) > 0;
    }
}
