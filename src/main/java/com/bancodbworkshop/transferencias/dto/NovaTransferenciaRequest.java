package com.bancodbworkshop.transferencias.dto;

import java.math.BigDecimal;

public record NovaTransferenciaRequest(Long contaOrigem, Long contaDestino, BigDecimal valor) {
}
