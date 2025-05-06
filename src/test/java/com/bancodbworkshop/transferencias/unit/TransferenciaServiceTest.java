package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.dto.TransferenciaRequest;
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
import org.springframework.test.util.ReflectionTestUtils;

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
    void deveRealizarTransferenciaComSucesso() {
        // Cenário
        BigDecimal valorTransferenciaEsperado = BigDecimal.valueOf(500.00);
        TransferenciaRequest request = criarTransferenciaRequest(1L, 2L, BigDecimal.valueOf(500.00));
        Conta contaOrigemEsperada = criarConta(1L, 1234, "João Silva", BigDecimal.valueOf(1000.00));
        Conta contaDestinoEsperada = criarConta(2L, 5678, "Maria Oliveira", BigDecimal.valueOf(2000.00));
        TransferenciaResponse transferenciaEsperada = new TransferenciaResponse(1L, 1L, 2L, valorTransferenciaEsperado, LocalDateTime.now());

        // Configuração dos mocks
        configurarMocksParaTransferencia(contaOrigemEsperada, contaDestinoEsperada);

        // Ação
        TransferenciaResponse respostaAtual = transferenciaService.transferir(request);

        // Verificação
        assertNotNull(respostaAtual);
        assertEquals(transferenciaEsperada.contaOrigem(), respostaAtual.contaOrigem());
        assertEquals(transferenciaEsperada.contaDestino(), respostaAtual.contaDestino());
        assertEquals(BigDecimal.valueOf(500.00), respostaAtual.valor());
        assertEquals(1L, respostaAtual.id());
        assertEquals(BigDecimal.valueOf(500.00), contaOrigemEsperada.getSaldo());
        assertEquals(BigDecimal.valueOf(2500.00), contaDestinoEsperada.getSaldo());
        verify(contaRepository, times(1)).save(contaOrigemEsperada);
        verify(contaRepository, times(1)).save(contaDestinoEsperada);
        verify(transferenciaRepository, times(1)).save(any(Transferencia.class));
    }

    @Test
    void deveLancarExcecaoQuandoSaldoInsuficiente() {
        // Cenário
        TransferenciaRequest request = criarTransferenciaRequest(1L, 2L, BigDecimal.valueOf(1500.00));
        Conta contaOrigem = criarConta(1L, 1234, "João Silva", BigDecimal.valueOf(1000.00));
        Conta contaDestino = criarConta(2L, 5678, "Maria Oliveira", BigDecimal.valueOf(2000.00));

        // Configuração dos mocks
        configurarMocksParaTransferencia(contaOrigem, contaDestino);

        // Ação e Verificação
        assertThrows(SaldoInsuficienteException.class, () -> transferenciaService.transferir(request));
        verificarNenhumaTransferenciaRealizada();
    }

    @Test
    void deveBuscarTransferenciaPorIdComSucesso() {
        // Cenário
        Transferencia transferenciaEsperada = criarTransferencia(1L, 1L, 2L, BigDecimal.valueOf(500.00));

        // Configuração dos mocks
        when(transferenciaRepository.findById(1L)).thenReturn(Optional.of(transferenciaEsperada));

        // Ação
        TransferenciaResponse respostaAtual = transferenciaService.buscarPorId(1L);

        // Verificação
        verificarTransferenciaEncontrada(respostaAtual, transferenciaEsperada);
    }

    @Test
    void deveLancarExcecaoQuandoTransferenciaNaoEncontrada() {
        // Cenário
        when(transferenciaRepository.findById(1L)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> transferenciaService.buscarPorId(1L));
        verify(transferenciaRepository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoContaOrigemNaoEncontrada() {
        // Cenário
        TransferenciaRequest request = criarTransferenciaRequest(1L, 2L, BigDecimal.valueOf(500.00));
        when(contaRepository.findById(1L)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> transferenciaService.transferir(request));
        verificarNenhumaTransferenciaRealizada();
    }

    @Test
    void deveLancarExcecaoQuandoContaDestinoNaoEncontrada() {
        // Cenário
        TransferenciaRequest request = criarTransferenciaRequest(1L, 2L, BigDecimal.valueOf(500.00));
        Conta contaOrigem = criarConta(1L, 1234, "João Silva", BigDecimal.valueOf(1000.00));

        // Configuração dos mocks
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(2L)).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> transferenciaService.transferir(request));
        verificarNenhumaTransferenciaRealizada();
    }

    @Test
    void quandoValorTransferenciaNegativoDeveLancarExcecao() {
        // Cenário
        TransferenciaRequest request = criarTransferenciaRequest(1L, 2L, BigDecimal.valueOf(-500.00));
        Conta contaOrigem = criarConta(1L, 1234, "João Silva", BigDecimal.valueOf(1000.00));
        Conta contaDestino = criarConta(2L, 5678, "Maria Oliveira", BigDecimal.valueOf(2000.00));

        // Configuração dos mocks
        configurarMocksParaTransferencia(contaOrigem, contaDestino);

        // Ação e Verificação
        assertThrows(IllegalArgumentException.class, () -> transferenciaService.transferir(request));
        verificarNenhumaTransferenciaRealizada();
    }

    private TransferenciaRequest criarTransferenciaRequest(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        return new TransferenciaRequest(contaOrigem, contaDestino, valor);
    }

    private Conta criarConta(Long id, int numero, String titular, BigDecimal saldo) {
        Conta conta = new Conta(numero, titular, saldo);
        ReflectionTestUtils.setField(conta, "id", id);
        return conta;
    }

    private Transferencia criarTransferencia(Long id, Long contaOrigem, Long contaDestino, BigDecimal valor) {
        Transferencia transferencia = new Transferencia(contaOrigem, contaDestino, valor, LocalDateTime.now());
        ReflectionTestUtils.setField(transferencia, "id", id);
        return transferencia;
    }

    private void configurarMocksParaTransferencia(Conta contaOrigem, Conta contaDestino) {
        when(contaRepository.findById(contaOrigem.getId())).thenReturn(Optional.of(contaOrigem));
        when(contaRepository.findById(contaDestino.getId())).thenReturn(Optional.of(contaDestino));
        when(transferenciaRepository.save(any(Transferencia.class))).thenAnswer(invocation -> {
                    Transferencia transferencia = invocation.getArgument(0);
                    ReflectionTestUtils.setField(transferencia, "id", 1L);
                    return transferencia;
                });
    }

    private void verificarTransferenciaEncontrada(TransferenciaResponse respostaAtual, Transferencia transferenciaEsperada) {
        assertNotNull(respostaAtual);
        assertEquals(transferenciaEsperada.getId(), respostaAtual.id());
        assertEquals(transferenciaEsperada.getContaOrigem(), respostaAtual.contaOrigem());
        assertEquals(transferenciaEsperada.getContaDestino(), respostaAtual.contaDestino());
        assertEquals(transferenciaEsperada.getValor(), respostaAtual.valor());
        verify(transferenciaRepository, times(1)).findById(transferenciaEsperada.getId());
    }

    private void verificarNenhumaTransferenciaRealizada() {
        verify(contaRepository, never()).save(any(Conta.class));
        verify(transferenciaRepository, never()).save(any(Transferencia.class));
    }
}