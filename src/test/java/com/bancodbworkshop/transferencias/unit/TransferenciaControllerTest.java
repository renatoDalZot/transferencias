package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.controller.TransferenciaController;
import com.bancodbworkshop.transferencias.dto.TransferenciaRequest;
import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.service.TransferenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransferenciaController.class)
class TransferenciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransferenciaService transferenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRealizarTransferenciaComSucesso() throws Exception {
        // Cenário
        Long contaOrigem = 1L;
        Long contaDestino = 2L;
        BigDecimal valorTransferencia = BigDecimal.valueOf(500.00);
        TransferenciaRequest request = criarTransferenciaRequest(contaOrigem, contaDestino, valorTransferencia);
        TransferenciaResponse transferenciaResponse = criarTransferenciaResponse(contaOrigem, contaDestino, valorTransferencia);

        // Configuração do mock
        Mockito.when(transferenciaService.transferir(any(TransferenciaRequest.class)))
               .thenReturn(transferenciaResponse);

        // Execução e verificação
        mockMvc.perform(post("/v1/transferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.contaOrigemId").value(contaOrigem))
               .andExpect(jsonPath("$.contaDestinoId").value(contaDestino))
               .andExpect(jsonPath("$.valor").value(valorTransferencia));
    }

    @Test
    void deveBuscarTransferenciaPorIdComSucesso() throws Exception {
        // Cenário
        Long contaOrigem = 1L;
        Long contaDestino = 2L;
        BigDecimal valorTransferencia = BigDecimal.valueOf(500.00);
        TransferenciaResponse transferenciaResponse = criarTransferenciaResponse(contaOrigem, contaDestino, valorTransferencia);

        // Configuração do mock
        Mockito.when(transferenciaService.buscarPorId(eq(1L))).thenReturn(transferenciaResponse);

        // Execução e verificação
        mockMvc.perform(get("/v1/transferencias/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.contaOrigemId").value(contaOrigem))
               .andExpect(jsonPath("$.contaDestinoId").value(contaDestino))
               .andExpect(jsonPath("$.valor").value(valorTransferencia));
    }

    @Test
    void quandoMalFormatadaRequisicaoDeveRetornarBadRequest() throws Exception {
        // Cenário
        TransferenciaRequest request = new TransferenciaRequest(null, null, null);

        // Execução e verificação
        mockMvc.perform(post("/v1/transferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());
    }

    private TransferenciaRequest criarTransferenciaRequest(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        return new TransferenciaRequest(contaOrigem, contaDestino, valor);
    }

    private TransferenciaResponse criarTransferenciaResponse(Long contaOrigem, Long contaDestino, BigDecimal valor) {
        return new TransferenciaResponse(1L, contaOrigem, contaDestino, valor, LocalDateTime.now());
    }
}