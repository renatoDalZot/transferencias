package com.bancodbworkshop.transferencias.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaResponse(Long id, Long contaOrigem, Long contaDestino, BigDecimal valor, LocalDateTime data) {
}
