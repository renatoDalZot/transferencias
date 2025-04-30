package com.bancodbworkshop.transferencias.service;

import com.bancodbworkshop.transferencias.dto.NovaTransferenciaRequest;
import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.exceptions.SaldoInsuficienteException;
import com.bancodbworkshop.transferencias.model.Conta;
import com.bancodbworkshop.transferencias.model.Transferencia;
import com.bancodbworkshop.transferencias.repository.ContaRepository;
import com.bancodbworkshop.transferencias.repository.TransferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferenciaService {

    private final ContaRepository contaRepository;
    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaResponse transferir(NovaTransferenciaRequest request) {
        if (request.valor() == null || request.valor().signum() <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        Conta origem = contaRepository.findById(request.contaOrigem())
                .orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(request.contaDestino())
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        origem.debitar(request.valor());
        destino.creditar(request.valor());

        contaRepository.save(origem);
        contaRepository.save(destino);

        Transferencia transferencia = new Transferencia(origem.getNumero(),
                destino.getNumero(),
                request.valor(),
                LocalDateTime.now());

        transferenciaRepository.save(transferencia);

        return transferencia.toResponse();
    }

    public TransferenciaResponse buscarPorId(Long id) {
        Transferencia t = transferenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transferência não encontrada"));


        return t.toResponse();
    }

}
