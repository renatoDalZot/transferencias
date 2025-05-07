package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.model.Transferencia;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransferenciaTest {

    @Test
    void deveCriarTransferenciaComSucesso() {
        // Cenário
        Long contaOrigemEsperada = 1L;
        Long contaDestinoEsperada = 2L;
        BigDecimal valorEsperado = BigDecimal.valueOf(500.00);
        LocalDateTime dataEsperada = LocalDateTime.now();

        // Ação
        Transferencia transferenciaAtual = new Transferencia(contaOrigemEsperada, contaDestinoEsperada, valorEsperado, dataEsperada);

        // Verificação
        assertEquals(contaOrigemEsperada, transferenciaAtual.getContaOrigemId());
        assertEquals(contaDestinoEsperada, transferenciaAtual.getContaDestinoId());
        assertEquals(valorEsperado, transferenciaAtual.getValor());
        assertEquals(dataEsperada, transferenciaAtual.getData());
    }
}