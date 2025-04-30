package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.model.Transferencia;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferenciaTest {

    @Test
    void deveConverterParaTransferenciaResponseCorretamente() {
        Long contaOrigem = 1L;
        Long contaDestino = 2L;
        BigDecimal valor = BigDecimal.valueOf(500.00);
        LocalDateTime data = LocalDateTime.of(2025, 4, 28, 10, 30, 0);

        Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor, data);
        TransferenciaResponse response = transferencia.toResponse();

        assertEquals(transferencia.getId(), response.id());
        assertEquals(transferencia.getContaOrigem(), response.contaOrigem());
        assertEquals(transferencia.getContaDestino(), response.contaDestino());
        assertEquals(transferencia.getValor(), response.valor());
        assertEquals(transferencia.getData(), response.data());
    }
}