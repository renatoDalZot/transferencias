package com.bancodbworkshop.transferencias.model;

import com.bancodbworkshop.transferencias.exceptions.SaldoInsuficienteException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "conta")
@NoArgsConstructor
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter(AccessLevel.PUBLIC)
    private Long id;
    @Column(name = "agencia")
    private Integer agencia;
    @Column(name = "titular")
    private String titular;
    @Getter(AccessLevel.PUBLIC)
    @Column(name = "saldo")
    private BigDecimal saldo;

    public Conta(Integer agencia, String titular, BigDecimal saldo) {
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = saldo;
    }

    public void debitar(BigDecimal valor) {
        if (valor.compareTo(this.saldo) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência");
        }
        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }
}
