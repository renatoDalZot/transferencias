package com.bancodbworkshop.transferencias.model;

import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transferencia")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long contaOrigem;
    private Long contaDestino;
    private BigDecimal valor;
    private LocalDateTime data;

    public TransferenciaResponse toResponse() {
        return new TransferenciaResponse(this.id, this.contaOrigem, this.contaDestino, this.valor, this.data);
    }

}
