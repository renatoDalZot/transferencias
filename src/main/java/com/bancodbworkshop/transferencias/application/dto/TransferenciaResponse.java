package com.bancodbworkshop.transferencias.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransferenciaResponse(Long id, Long contaOrigemId, Long contaDestinoId, BigDecimal valor, LocalDateTime data) {
}
