package com.bancodbworkshop.transferencias.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaRequest(@NotNull @Positive(message = "contaOrigem é obrigatória") Long contaOrigem,
                                   @NotNull @Positive(message = "contaDestino é obrigatória") Long contaDestino,
                                   @NotNull @Positive(message = "Valor deve ser positivo") BigDecimal valor) {
}
