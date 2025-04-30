package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.dto.NovaTransferenciaRequest;
import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.exceptions.SaldoInsuficienteException;
import com.bancodbworkshop.transferencias.model.Conta;
import com.bancodbworkshop.transferencias.model.Transferencia;
import com.bancodbworkshop.transferencias.repository.ContaRepository;
import com.bancodbworkshop.transferencias.repository.TransferenciaRepository;
import com.bancodbworkshop.transferencias.service.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void dadoRecursosValidosDeveRealizarTransferenciaComSucesso() {
        NovaTransferenciaRequest request = new NovaTransferenciaRequest(1L, 2L, BigDecimal.valueOf(500.00));
        Conta contaOrigem = new Conta(1234, "João Silva", BigDecimal.valueOf(1000.00));
        Conta contaDestino = new Conta(5678, "Maria Oliveira", BigDecimal.valueOf(2000.00));

        try {
            var idField = Conta.class.getDeclaredField("numero");
            idField.setAccessible(true);
            idField.set(contaOrigem, 1L);
            idField.set(contaDestino, 2L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(2L)).thenReturn(Optional.of(contaDestino));

        Transferencia transferencia = new Transferencia(1L, 2L, BigDecimal.valueOf(500.00), LocalDateTime.now());
        when(transferenciaRepository.save(any(Transferencia.class))).thenReturn(transferencia);

        TransferenciaResponse response = transferenciaService.transferir(request);

        assertNotNull(response);
        assertEquals(1L, response.contaOrigem());
        assertEquals(2L, response.contaDestino());
        assertEquals(BigDecimal.valueOf(500.00), response.valor());

        verify(contaRepository, times(1)).save(contaOrigem);
        verify(contaRepository, times(1)).save(contaDestino);
        verify(transferenciaRepository, times(1)).save(any(Transferencia.class));
    }

    @Test
    void dadoSaldoInsuficienteQuandoTrasnferirDeveLancarExcecao() {
        NovaTransferenciaRequest request = new NovaTransferenciaRequest(1L, 2L, BigDecimal.valueOf(1500.00));
        Conta contaOrigem = new Conta(1234, "João Silva", BigDecimal.valueOf(1000.00));
        Conta contaDestino = new Conta(5678, "Maria Oliveira", BigDecimal.valueOf(2000.00));

        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(2L)).thenReturn(Optional.of(contaDestino));

        assertThrows(SaldoInsuficienteException.class, () -> transferenciaService.transferir(request));

        verify(contaRepository, never()).save(any(Conta.class));
        verify(transferenciaRepository, never()).save(any(Transferencia.class));
    }

    @Test
    void deveBuscarTransferenciaPorIdComSucesso() {
        Transferencia transferencia = new Transferencia(1L, 2L, BigDecimal.valueOf(500.00), LocalDateTime.now());

        try {
            var idField = Transferencia.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(transferencia, 1L);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(transferencia));

        TransferenciaResponse response = transferenciaService.buscarPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals(1L, response.contaOrigem());
        assertEquals(2L, response.contaDestino());
        assertEquals(BigDecimal.valueOf(500.00), response.valor());

        verify(transferenciaRepository, times(1)).findById(1L);
    }

    @Test
    void dadoTrasnferenciaInexistenteQuandoBuscaDeveLancarExcecao() {
        when(transferenciaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transferenciaService.buscarPorId(1L));

        verify(transferenciaRepository, times(1)).findById(1L);
    }
}