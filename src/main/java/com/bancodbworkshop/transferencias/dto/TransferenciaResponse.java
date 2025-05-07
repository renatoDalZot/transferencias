package com.bancodbworkshop.transferencias.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaResponse(Long id, Long contaOrigemId, Long contaDestinoId, BigDecimal valor, LocalDateTime data) {
}
