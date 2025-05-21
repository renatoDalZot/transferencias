package com.bancodbworkshop.transferencias.application.service;

import com.bancodbworkshop.transferencias.application.dto.TransferenciaRequest;
import com.bancodbworkshop.transferencias.application.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.domain.model.Conta;
import com.bancodbworkshop.transferencias.domain.model.Transferencia;
import com.bancodbworkshop.transferencias.domain.repository.ContaRepository;
import com.bancodbworkshop.transferencias.domain.repository.TransferenciaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final ContaRepository contaRepository;
    private final TransferenciaRepository transferenciaRepository;

    @Transactional
    public TransferenciaResponse transferir(TransferenciaRequest request) {
        if (request.valor() == null || request.valor().signum() <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        Conta origem = contaRepository.findById(request.contaOrigemId())
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(request.contaDestinoId())
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        origem.debitar(request.valor());
        destino.creditar(request.valor());

        contaRepository.save(origem);
        contaRepository.save(destino);

        Transferencia transferencia = new Transferencia(origem.getId(),
                destino.getId(),
                request.valor(),
                LocalDateTime.now());

        transferenciaRepository.save(transferencia);

        return toResponse(transferencia);
    }

    public TransferenciaResponse buscarPorId(Long id) {
        Transferencia t = transferenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transferência não encontrada"));


        return toResponse(t);
    }

    private TransferenciaResponse toResponse(Transferencia transferencia) {
        return new TransferenciaResponse(
                transferencia.getId(),
                transferencia.getContaOrigemId(),
                transferencia.getContaDestinoId(),
                transferencia.getValor(),
                transferencia.getData()
        );
    }

}
