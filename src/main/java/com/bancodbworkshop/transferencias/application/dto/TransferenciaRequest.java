package com.bancodbworkshop.transferencias.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferenciaRequest(@NotNull @Positive(message = "contaOrigemId é obrigatória") Long contaOrigemId,
                                   @NotNull @Positive(message = "contaDestinoId é obrigatória") Long contaDestinoId,
                                   @NotNull @Positive(message = "Valor deve ser positivo") BigDecimal valor) {
}
