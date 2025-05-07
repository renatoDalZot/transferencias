package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.model.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ContaTest {

    private Conta conta;

    @BeforeEach
    void setUp() {
        conta = new Conta(1234, "João Silva", BigDecimal.valueOf(1000.00));
    }

    @Test
    void quandoSaldoSuficienteDeveDebitar() {
        BigDecimal valorDebitoEsperado = BigDecimal.valueOf(500.00);

        conta.debitar(valorDebitoEsperado);

        assertEquals(valorDebitoEsperado, conta.getSaldo());
    }

    @Test
    void quandoSaldoInsuficienteDeveLancarExcecao() {
        BigDecimal valorDebito = BigDecimal.valueOf(1500.00);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                conta.debitar(valorDebito)
        );
        assertEquals("Saldo insuficiente para realizar a transferência", exception.getMessage());
    }

    @Test
    void quandoCreditarDeveIncrementarSaldo() {
        BigDecimal valorCredito = BigDecimal.valueOf(200.00);
        BigDecimal valorEsperado = BigDecimal.valueOf(1200.00);

        conta.creditar(valorCredito);

        assertEquals(valorEsperado, conta.getSaldo());
    }
}