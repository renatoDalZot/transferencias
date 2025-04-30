package com.bancodbworkshop.transferencias.unit;

import com.bancodbworkshop.transferencias.controller.TransferenciaController;
import com.bancodbworkshop.transferencias.dto.NovaTransferenciaRequest;
import com.bancodbworkshop.transferencias.dto.TransferenciaResponse;
import com.bancodbworkshop.transferencias.service.TransferenciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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

    private TransferenciaResponse transferenciaResponse;

    @BeforeEach
    void setUp() {
        transferenciaResponse = new TransferenciaResponse(
            1L, 1L, 2L, BigDecimal.valueOf(500.00), LocalDateTime.now()
        );
    }

    @Test
    void deveRealizarTransferenciaComSucesso() throws Exception {
        NovaTransferenciaRequest request = new NovaTransferenciaRequest(1L, 2L, BigDecimal.valueOf(500.00));

        Mockito.when(transferenciaService.transferir(any(NovaTransferenciaRequest.class)))
               .thenReturn(transferenciaResponse);

        mockMvc.perform(post("/v1/transferencias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.contaOrigem").value(1L))
               .andExpect(jsonPath("$.contaDestino").value(2L))
               .andExpect(jsonPath("$.valor").value(500.00));
    }

    @Test
    void deveBuscarTransferenciaPorIdComSucesso() throws Exception {
        Mockito.when(transferenciaService.buscarPorId(eq(1L))).thenReturn(transferenciaResponse);

        mockMvc.perform(get("/v1/transferencias/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.contaOrigem").value(1L))
               .andExpect(jsonPath("$.contaDestino").value(2L))
               .andExpect(jsonPath("$.valor").value(500.00));
    }
}